package com.vanotech.experiments.data.lunardates.internal.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
internal interface EventDao {

    @Delete
    suspend fun delete(item: EventEntity)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    suspend fun get(id: Int): EventEntity?

    @Query("SELECT * FROM $TABLE_NAME ORDER BY month, dayOfMonth")
    fun getAllAsPagingSource(): PagingSource<Int, EventEntity>

    @Upsert
    suspend fun upsert(item: EventEntity)

    @Upsert
    suspend fun upsert(items: Collection<EventEntity>)

    companion object {
        private const val TABLE_NAME = EventEntity.TABLE_NAME
    }
}