package com.vanotech.experiments.data.tvguide.internal.db

import com.vanotech.experiments.data.tvguide.Listing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Singleton


@Singleton
internal class ListingDaoService(
    private val dao: ListingDao
) {
    fun getAllAsPagingSource() = dao.getAllAsPagingSource()

    fun getAsFlow(id: String): Flow<Listing?> = dao.getAsFlow(id).map {
        it?.toListing()
    }

    suspend fun upsert(item: Listing) = dao.upsert(item.toListingEntity())
}