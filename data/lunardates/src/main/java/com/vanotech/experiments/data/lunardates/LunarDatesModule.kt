package com.vanotech.experiments.data.lunardates

import android.content.Context
import com.vanotech.experiments.data.lunardates.internal.EventRepoImpl
import com.vanotech.experiments.data.lunardates.internal.db.EventDao
import com.vanotech.experiments.data.lunardates.internal.db.LunarDatesDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [LunarDatesModule.Bindings::class])
@InstallIn(SingletonComponent::class)
object LunarDatesModule {

    @Module
    @InstallIn(SingletonComponent::class)
    internal interface Bindings {
        @Binds
        fun bindsEventRepo(eventRepo: EventRepoImpl): EventRepo
    }

    @Provides
    @Singleton
    internal fun providesLunarDatesDatabase(@ApplicationContext context: Context): LunarDatesDatabase {
        return LunarDatesDatabase.getInstance(context)
    }

    @Provides
    internal fun providesEventDao(database: LunarDatesDatabase): EventDao {
        return database.eventDao()
    }
}