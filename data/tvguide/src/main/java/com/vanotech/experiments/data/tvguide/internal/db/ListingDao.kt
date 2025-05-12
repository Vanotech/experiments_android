package com.vanotech.experiments.data.tvguide.internal.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.vanotech.experiments.data.tvguide.schema.Listing
import kotlinx.coroutines.flow.Flow


@Dao
interface ListingDao {

    @Delete
    suspend fun delete(item: Listing)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TABLE_NAME ORDER BY startAt, duration")
    fun getAllAsPagingSource(): PagingSource<Int, Listing>

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    fun getAsFlow(id: String): Flow<Listing>

    @Upsert
    suspend fun upsert(item: Listing)

    @Upsert
    suspend fun upsert(items: List<Listing>)

    companion object {
        private const val TABLE_NAME = Listing.TABLE_NAME
    }
}