package com.vanotech.experiments.data.tvguide.internal.db

import org.koin.core.annotation.Singleton


@Singleton
internal class ScheduleDaoService(
    private val dao: ScheduleDao
) {
    suspend fun deleteAll() = dao.deleteAll()

    suspend fun upsert(item: ScheduleEntity) = dao.upsert(item)

    suspend fun upsert(item: SchedulePartial) = dao.upsert(item)

    suspend fun upsert(items: Collection<ScheduleEntity>) = dao.upsert(items)
}