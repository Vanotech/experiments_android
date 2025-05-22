package com.vanotech.experiments.data.tvguide.internal.db

import com.vanotech.experiments.data.tvguide.schema.Listing
import javax.inject.Inject


internal class ListingDaoService @Inject constructor(
    private val dao: ListingDao
) {
    suspend fun deleteAll() = dao.deleteAll()

    fun getAllAsPagingSource() = dao.getAllAsPagingSource()

    fun getAsFlow(id: String) = dao.getAsFlow(id)

    suspend fun upsert(item: Listing) = dao.upsert(item)

    suspend fun upsert(items: List<Listing>) = dao.upsert(items)
}