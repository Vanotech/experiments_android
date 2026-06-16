package com.vanotech.experiments.data.tvguide.internal.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert


@Dao
internal interface ChannelDao {

    @Delete
    suspend fun delete(item: ChannelEntity)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll()

    @Upsert
    suspend fun upsert(item: ChannelEntity)

    @Upsert
    suspend fun upsert(items: Collection<ChannelEntity>)

    companion object {
        const val TABLE_NAME = ChannelEntity.TABLE_NAME
    }
}