package com.vanotech.experiments.data.lunardates.internal.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = EventEntity.TABLE_NAME)
internal data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val dayOfMonth: Int,
    val month: Int
) {
    companion object {
        const val TABLE_NAME = "event"
    }
}