package com.vanotech.experiments.data.tvguide

import android.content.Context
import com.vanotech.experiments.data.tvguide.internal.ListingRepoImpl
import com.vanotech.experiments.data.tvguide.internal.db.ListingDao
import com.vanotech.experiments.data.tvguide.internal.db.RemoteKeyDao
import com.vanotech.experiments.data.tvguide.internal.db.TvGuideDatabase
import com.vanotech.experiments.data.tvguide.internal.net.TvGuideApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
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
    internal fun providesTvGuideDatabase(@ApplicationContext context: Context): TvGuideDatabase {
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
    @Singleton
    internal fun providesJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
        }
    }

    @Provides
    @Singleton
    internal fun providesHttpClient(json: Json): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(json)
            }
        }
    }

    @Provides
    internal fun providesTvGuideApiService(httpClient: HttpClient): TvGuideApiService {
        return TvGuideApiService(httpClient)
    }
}