package com.vanotech.experiments.data.tvguide

import android.content.Context
import com.vanotech.experiments.data.tvguide.internal.db.ChannelDao
import com.vanotech.experiments.data.tvguide.internal.db.ListingDao
import com.vanotech.experiments.data.tvguide.internal.db.RemoteKeyDao
import com.vanotech.experiments.data.tvguide.internal.db.ScheduleDao
import com.vanotech.experiments.data.tvguide.internal.db.TvGuideDatabase
import com.vanotech.experiments.data.tvguide.internal.net.TvGuideApiService
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module
@ComponentScan
class TvGuideDataModule {

    @Singleton
    internal fun providesTvGuideDatabase(context: Context): TvGuideDatabase {
        return TvGuideDatabase.getInstance(context)
    }

    @Singleton
    internal fun providesChannelDao(database: TvGuideDatabase): ChannelDao {
        return database.channelDao()
    }

    @Singleton
    internal fun providesListingDao(database: TvGuideDatabase): ListingDao {
        return database.listingDao()
    }

    @Singleton
    internal fun providesScheduleDao(database: TvGuideDatabase): ScheduleDao {
        return database.scheduleDao()
    }

    @Singleton
    internal fun providesRemoteKeyDao(database: TvGuideDatabase): RemoteKeyDao {
        return database.remoteKeyDao()
    }

    @Singleton
    internal fun providesJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
        }
    }

    @Singleton
    internal fun providesHttpClient(json: Json): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(json)
            }
        }
    }

    @Singleton
    internal fun providesTvGuideApiService(httpClient: HttpClient): TvGuideApiService {
        return TvGuideApiService(httpClient)
    }
}