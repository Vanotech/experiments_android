package com.vanotech.experiments.data.tvguide.internal

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.vanotech.experiments.data.tvguide.internal.db.ListingView
import com.vanotech.experiments.data.tvguide.internal.db.RemoteKeyEntity
import com.vanotech.experiments.data.tvguide.internal.db.TvGuideDatabase
import com.vanotech.experiments.data.tvguide.internal.net.TvGuideApiService
import com.vanotech.experiments.data.tvguide.internal.net.toChannelEntity
import com.vanotech.experiments.data.tvguide.internal.net.toScheduleEntity
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import java.io.IOException
import kotlin.time.Duration.Companion.hours
import kotlin.time.Instant

@OptIn(ExperimentalPagingApi::class)
internal class GetListingsRemoteMediator(
    private val platform: String,
    private val region: String,
    private val instant: Instant,
    private val apiService: TvGuideApiService,
    private val database: TvGuideDatabase
) : RemoteMediator<Int, ListingView>() {
    private val channelDao = database.channelDao()
    private val scheduleDao = database.scheduleDao()
    private val remoteKeyDao = database.remoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        val createdAt = remoteKeyDao.getCreationTime() ?: 0
        val timeToLive = 1.hours.inWholeMilliseconds
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
        state: PagingState<Int, ListingView>
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
                    remoteKey.loadKey + 6.hours
                }
            }

            val listings = apiService.fetchListings(
                platform = platform,
                region = region,
                instant = loadKey
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    channelDao.deleteAll()
                    scheduleDao.deleteAll()
                    remoteKeyDao.deleteAll()
                }

                val channels = listings.map {
                    it.toChannelEntity()
                }
                channelDao.upsert(channels)

                val schedules = listings.flatMap { channel ->
                    channel.schedules.map {
                        it.toScheduleEntity(channel)
                    }
                }
                scheduleDao.upsert(schedules)

                val remoteKeys = schedules.map {
                    RemoteKeyEntity(
                        id = it.id,
                        loadKey = loadKey
                    )
                }
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

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ListingView>): RemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeyDao.get(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ListingView>): RemoteKeyEntity? {
        return state.firstItemOrNull()?.let { item ->
            remoteKeyDao.get(item.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ListingView>): RemoteKeyEntity? {
        return state.lastItemOrNull()?.let { item ->
            remoteKeyDao.get(item.id)
        }
    }
}