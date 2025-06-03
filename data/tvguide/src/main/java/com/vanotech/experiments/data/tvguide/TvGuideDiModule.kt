package com.vanotech.experiments.data.tvguide

import android.content.Context
import com.vanotech.experiments.data.tvguide.internal.db.ListingDao
import com.vanotech.experiments.data.tvguide.internal.db.TvGuideDatabase
import com.vanotech.experiments.data.tvguide.internal.net.TvGuideApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object TvGuideDiModule {

    @Provides
    @Singleton
    fun providesListingDatabase(@ApplicationContext context: Context): TvGuideDatabase {
        return TvGuideDatabase.getInstance(context)
    }

    @Provides
    fun providesListingDao(database: TvGuideDatabase): ListingDao {
        return database.listingDao()
    }

    @Provides
    fun providesTvGuideApi(): TvGuideApi {
        return TvGuideApi.getInstance()
    }
}