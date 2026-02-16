package com.vanotech.experiments.data.tvguide.internal.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
internal interface ListingDao {

    @Delete
    suspend fun delete(item: ListingEntity)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TABLE_NAME ORDER BY startAt, duration")
    fun getAllAsPagingSource(): PagingSource<Int, ListingEntity>

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    fun getAsFlow(id: String): Flow<ListingEntity?>

    @Upsert
    suspend fun upsert(item: ListingEntity)

    @Upsert
    suspend fun upsert(items: Collection<ListingEntity>)

    companion object {
        private const val TABLE_NAME = ListingEntity.TABLE_NAME
    }
}