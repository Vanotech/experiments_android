package com.vanotech.experiments.data.tvguide.internal.db

import org.koin.core.annotation.Singleton


@Singleton
internal class ChannelDaoService(
    private val dao: ChannelDao
) {
    suspend fun deleteAll() = dao.deleteAll()

    suspend fun upsert(item: ChannelEntity) = dao.upsert(item)

    suspend fun upsert(items: Collection<ChannelEntity>) = dao.upsert(items)
}