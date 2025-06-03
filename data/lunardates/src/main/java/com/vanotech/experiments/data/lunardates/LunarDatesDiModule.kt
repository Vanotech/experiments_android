package com.vanotech.experiments.data.lunardates

import android.content.Context
import com.vanotech.experiments.data.lunardates.internal.db.EventDao
import com.vanotech.experiments.data.lunardates.internal.db.LunarDatesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object LunarDatesDiModule {

    @Provides
    @Singleton
    fun providesEventDatabase(@ApplicationContext context: Context): LunarDatesDatabase {
        return LunarDatesDatabase.getInstance(context)
    }

    @Provides
    fun providesEventDao(database: LunarDatesDatabase): EventDao {
        return database.eventDao()
    }
}