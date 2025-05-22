package com.vanotech.experiments.data.lunardates.internal.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.vanotech.experiments.data.lunardates.Event


@Dao
internal interface EventDao {

    @Delete
    suspend fun delete(item: Event)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    suspend fun get(id: Int): Event?

    @Query("SELECT * FROM $TABLE_NAME ORDER BY month, dayOfMonth")
    fun getAllAsPagingSource(): PagingSource<Int, Event>

    @Upsert
    suspend fun upsert(item: Event)

    @Upsert
    suspend fun upsert(items: List<Event>)

    companion object {
        private const val TABLE_NAME = Event.TABLE_NAME
    }
}