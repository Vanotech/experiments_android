package com.vanotech.experiments.data.tvguide.local.db

import org.koin.core.annotation.Singleton


@Singleton
internal class ListingDaoService(
    private val dao: ListingDao
) {
    fun getAllAsPagingSource() = dao.getAllAsPagingSource()

    fun getAsFlow(id: String) = dao.getAsFlow(id)
}