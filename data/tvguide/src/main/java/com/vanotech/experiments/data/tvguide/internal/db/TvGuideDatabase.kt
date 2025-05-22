package com.vanotech.experiments.data.tvguide.internal.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vanotech.experiments.core.utils.room.TimeConverters
import com.vanotech.experiments.data.tvguide.schema.Listing

@Database(
    entities = [Listing::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(TimeConverters::class)
internal abstract class TvGuideDatabase : RoomDatabase() {
    abstract fun listingDao(): ListingDao

    companion object {
        fun getInstance(context: Context): TvGuideDatabase {
            return Room.inMemoryDatabaseBuilder(context.applicationContext, TvGuideDatabase::class.java)
                .fallbackToDestructiveMigration(false)
                .build()
        }
    }
}