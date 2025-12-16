package com.vanotech.experiments.data.tvguide.internal.net

import com.vanotech.experiments.data.tvguide.internal.net.schema.Channel
import com.vanotech.experiments.data.tvguide.internal.net.schema.SingleResponse
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

internal interface TvGuideApi {

    @GET("listings")
    suspend fun getListings(
        @Query("platform") platform: String,
        @Query("region") region: String,
        @Query("date") date: String,
        @Query("hour") hour: Int,
        @Query("details") details: Boolean
    ): List<Channel>

    @GET("single")
    suspend fun getSingle(
        @Query("pa_id") paId: String
    ): SingleResponse

    companion object {
        private const val BASE_URL = "https://api-2.tvguide.co.uk/"

        fun getInstance(
            baseUrl: String = BASE_URL
        ): TvGuideApi {
            val jsonConverterFactory = Json {
                ignoreUnknownKeys = true
            }.asConverterFactory(
                "application/json; charset=utf-8".toMediaType()
            )

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(jsonConverterFactory)
                .build()

            return retrofit.create(TvGuideApi::class.java)
        }
    }
}