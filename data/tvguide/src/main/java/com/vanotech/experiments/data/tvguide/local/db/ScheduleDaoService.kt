package com.vanotech.experiments.data.tvguide.local.db

import com.vanotech.experiments.data.tvguide.local.db.model.ScheduleEntity
import com.vanotech.experiments.data.tvguide.local.db.model.SchedulePartial
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