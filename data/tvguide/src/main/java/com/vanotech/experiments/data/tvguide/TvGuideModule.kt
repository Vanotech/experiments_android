package com.vanotech.experiments.data.tvguide

import android.content.Context
import com.vanotech.experiments.data.tvguide.internal.ListingRepoImpl
import com.vanotech.experiments.data.tvguide.internal.db.ListingDao
import com.vanotech.experiments.data.tvguide.internal.db.RemoteKeyDao
import com.vanotech.experiments.data.tvguide.internal.db.TvGuideDatabase
import com.vanotech.experiments.data.tvguide.internal.net.TvGuideApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TvGuideModule {

    @Module
    @InstallIn(SingletonComponent::class)
    internal interface Bindings {
        @Binds
        fun bindsListingRepo(listingRepo: ListingRepoImpl): ListingRepo
    }

    @Provides
    @Singleton
    internal fun providesListingDatabase(@ApplicationContext context: Context): TvGuideDatabase {
        return TvGuideDatabase.getInstance(context)
    }

    @Provides
    internal fun providesListingDao(database: TvGuideDatabase): ListingDao {
        return database.listingDao()
    }

    @Provides
    internal fun providesRemoteKeyDao(database: TvGuideDatabase): RemoteKeyDao {
        return database.remoteKeyDao()
    }

    @Provides
    internal fun providesTvGuideApi(): TvGuideApi {
        return TvGuideApi.getInstance()
    }
}