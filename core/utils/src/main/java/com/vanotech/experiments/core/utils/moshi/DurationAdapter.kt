package com.vanotech.experiments.core.utils.moshi

import android.os.Build
import androidx.annotation.RequiresApi
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.vanotech.experiments.core.utils.annotations.Hours
import com.vanotech.experiments.core.utils.annotations.Milliseconds
import com.vanotech.experiments.core.utils.annotations.Minutes
import com.vanotech.experiments.core.utils.annotations.Seconds
import java.time.Duration

class DurationAdapter {
    @FromJson
    @Milliseconds
    fun fromJsonAsMilliseconds(value: Long?): Duration? {
        return value?.let { Duration.ofMillis(it) }
    }

    @ToJson
    fun toJsonAsMilliseconds(@Milliseconds duration: Duration?): Long? {
        return duration?.toMillis()
    }

    @FromJson
    @Seconds
    fun fromJsonAsSeconds(value: Long?): Duration? {
        return value?.let { Duration.ofSeconds(it) }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @ToJson
    fun toJsonAsSeconds(@Seconds duration: Duration?): Long? {
        return duration?.toSeconds()
    }

    @FromJson
    @Minutes
    fun fromJsonAsMinutes(value: Long?): Duration? {
        return value?.let { Duration.ofMinutes(it) }
    }

    @ToJson
    fun toJsonAsMinutes(@Minutes duration: Duration?): Long? {
        return duration?.toMinutes()
    }

    @FromJson
    @Hours
    fun fromJsonAsHours(value: Long?): Duration? {
        return value?.let { Duration.ofHours(it) }
    }

    @ToJson
    fun toJsonAsHours(@Hours duration: Duration?): Long? {
        return duration?.toHours()
    }
}