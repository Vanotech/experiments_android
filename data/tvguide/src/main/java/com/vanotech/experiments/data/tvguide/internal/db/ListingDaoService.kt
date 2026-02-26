package com.vanotech.experiments.data.tvguide.internal.db

import com.vanotech.experiments.data.tvguide.Listing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


internal class ListingDaoService @Inject constructor(
    private val dao: ListingDao
) {
    fun getAllAsPagingSource() = dao.getAllAsPagingSource()

    fun getAsFlow(id: String): Flow<Listing?> = dao.getAsFlow(id).map {
        it?.toListing()
    }

    suspend fun upsert(item: Listing) = dao.upsert(item.toListingEntity())
}