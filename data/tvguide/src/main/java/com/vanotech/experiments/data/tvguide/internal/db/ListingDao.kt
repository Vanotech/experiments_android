package com.vanotech.experiments.data.tvguide.internal.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ListingDao {
    @Query("SELECT * FROM $TABLE_NAME ORDER BY startAt, duration")
    fun getAllAsPagingSource(): PagingSource<Int, ListingView>

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    fun getAsFlow(id: String): Flow<ListingView?>

    companion object {
        private const val TABLE_NAME = ListingView.TABLE_NAME
    }
}