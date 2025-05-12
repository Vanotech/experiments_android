package com.vanotech.experiments.data.lunardates

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = Event.TABLE_NAME)
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val dayOfMonth: Int,
    val month: Int
) {
    companion object {
        const val TABLE_NAME = "event"

        fun mockData(index: Int): Event {
            return Event(
                id = index,
                title = "Title $index",
                dayOfMonth = index,
                month = index
            )
        }
    }
}