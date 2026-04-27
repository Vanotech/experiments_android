package com.vanotech.experiments.data.tvguide.internal.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Instant

@Entity(tableName = RemoteKeyEntity.TABLE_NAME)
internal data class RemoteKeyEntity(
    @PrimaryKey val id: String,
    val loadKey: Instant,
    val createdAt: Long = System.currentTimeMillis()
) {
    companion object {
        const val TABLE_NAME = "remote_keys"
    }
}