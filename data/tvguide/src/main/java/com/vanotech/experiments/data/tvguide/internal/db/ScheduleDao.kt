package com.vanotech.experiments.data.tvguide.internal.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert


@Dao
internal interface ScheduleDao {

    @Delete
    suspend fun delete(item: ScheduleEntity)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll()

    @Upsert
    suspend fun upsert(item: ScheduleEntity)

    @Upsert(entity = ScheduleEntity::class)
    suspend fun upsert(item: SchedulePartial)

    @Upsert
    suspend fun upsert(items: Collection<ScheduleEntity>)

    companion object {
        const val TABLE_NAME = ScheduleEntity.TABLE_NAME
    }
}