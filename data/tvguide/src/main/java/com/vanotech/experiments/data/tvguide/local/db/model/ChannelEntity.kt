package com.vanotech.experiments.data.tvguide.local.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ChannelEntity.TABLE_NAME)
data class ChannelEntity(
    @PrimaryKey val id: String,
    val title: String,
    val logoUrl: String?
) {
    companion object {
        const val TABLE_NAME = "channels"
    }
}