package com.vanotech.experiments.data.tvguide.local.db

import com.vanotech.experiments.data.tvguide.local.db.model.ChannelEntity
import org.koin.core.annotation.Singleton


@Singleton
internal class ChannelDaoService(
    private val dao: ChannelDao
) {
    suspend fun deleteAll() = dao.deleteAll()

    suspend fun upsert(item: ChannelEntity) = dao.upsert(item)

    suspend fun upsert(items: Collection<ChannelEntity>) = dao.upsert(items)
}