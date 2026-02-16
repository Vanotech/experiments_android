package com.vanotech.experiments.data.tvguide.internal

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.vanotech.experiments.data.tvguide.internal.db.ListingEntity
import com.vanotech.experiments.data.tvguide.internal.db.RemoteKeyEntity
import com.vanotech.experiments.data.tvguide.internal.db.TvGuideDatabase
import com.vanotech.experiments.data.tvguide.internal.db.toListingEntity
import com.vanotech.experiments.data.tvguide.internal.net.TvGuideApiService
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import java.io.IOException
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
internal class GetListingsRemoteMediator(
    private val platform: String,
    private val region: String,
    private val instant: Instant,
    private val apiService: TvGuideApiService,
    private val database: TvGuideDatabase
) : RemoteMediator<Int, ListingEntity>() {
    private val listingDao = database.listingDao()
    private val remoteKeyDao = database.remoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        val createdAt = remoteKeyDao.getCreationTime() ?: 0
        val timeToLive = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        val expiresAt = createdAt + timeToLive
        val now = System.currentTimeMillis()
        return if (expiresAt < now) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ListingEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKey?.loadKey ?: instant
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)
                    if (remoteKey == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }
                    remoteKey.loadKey.plus(6, ChronoUnit.HOURS)
                }
            }

            val listings = apiService.getListings(
                platform = platform,
                region = region,
                instant = loadKey
            ).map {
                it.toListingEntity()
            }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    listingDao.deleteAll()
                    remoteKeyDao.deleteAll()
                }

                val remoteKeys = listings.map {
                    RemoteKeyEntity(
                        id = it.id,
                        loadKey = loadKey
                    )
                }

                listingDao.upsert(listings)
                remoteKeyDao.insert(remoteKeys)
            }

            MediatorResult.Success(
                endOfPaginationReached = listings.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: ClientRequestException) {
            MediatorResult.Error(e)
        } catch (e: ServerResponseException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ListingEntity>): RemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeyDao.get(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ListingEntity>): RemoteKeyEntity? {
        return state.firstItemOrNull()?.let { item ->
            remoteKeyDao.get(item.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ListingEntity>): RemoteKeyEntity? {
        return state.lastItemOrNull()?.let { item ->
            remoteKeyDao.get(item.id)
        }
    }
}