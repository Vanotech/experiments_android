package com.vanotech.experiments.data.tvguide.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vanotech.experiments.data.tvguide.local.db.model.RemoteKeyEntity


@Dao
internal interface RemoteKeyDao {

    @Delete
    suspend fun delete(item: RemoteKeyEntity)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    suspend fun get(id: String): RemoteKeyEntity?

    @Query("SELECT createdAt From $TABLE_NAME ORDER BY createdAt DESC LIMIT 1")
    suspend fun getCreationTime(): Long?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: RemoteKeyEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(items: Collection<RemoteKeyEntity>)

    companion object {
        private const val TABLE_NAME = RemoteKeyEntity.TABLE_NAME
    }
}