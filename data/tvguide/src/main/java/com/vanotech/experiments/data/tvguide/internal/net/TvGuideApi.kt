package com.vanotech.experiments.data.tvguide.internal.net

import com.squareup.moshi.Moshi
import com.vanotech.experiments.data.tvguide.internal.net.schema.Channel
import com.vanotech.experiments.data.tvguide.internal.net.schema.ProgramResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TvGuideApi {

    @GET("listings")
    suspend fun getListings(
        @Query("platform") platform: String,
        @Query("region") region: String,
        @Query("date") date: String,
        @Query("hour") hour: Int,
        @Query("details") details: Boolean
    ): List<Channel>

    @GET("single")
    suspend fun getProgram(
        @Query("pa_id") paId: String
    ): ProgramResponse

    companion object {
        private const val BASE_URL = "https://api-2.tvguide.co.uk/"

        fun getInstance(
            baseUrl: String = BASE_URL
        ): TvGuideApi {
            val moshi = Moshi.Builder()
                .add(com.vanotech.experiments.core.utils.moshi.InstantAdapter())
                .add(com.vanotech.experiments.core.utils.moshi.DurationAdapter())
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

            return retrofit.create(TvGuideApi::class.java)
        }
    }
}