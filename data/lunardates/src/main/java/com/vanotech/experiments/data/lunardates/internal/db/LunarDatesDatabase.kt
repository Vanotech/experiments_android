package com.vanotech.experiments.data.lunardates.internal.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vanotech.experiments.data.lunardates.Event

@Database(
    entities = [Event::class],
    exportSchema = false,
    version = 1
)
internal abstract class LunarDatesDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {
        fun getInstance(context: Context): LunarDatesDatabase {
            return Room.inMemoryDatabaseBuilder(context.applicationContext, LunarDatesDatabase::class.java)
                .fallbackToDestructiveMigration(false)
                .build()
        }
    }
}