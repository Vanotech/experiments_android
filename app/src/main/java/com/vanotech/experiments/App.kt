package com.vanotech.experiments

import android.app.Application
import com.vanotech.experiments.feature.camera.CameraModule
import com.vanotech.experiments.feature.media.MediaModule
import com.vanotech.experiments.feature.tvguide.TvGuideModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.KoinApplication
import org.koin.plugin.module.dsl.startKoin
import timber.log.Timber

@KoinApplication(
    modules = [
        AppModule::class,
        CameraModule::class,
        MediaModule::class,
        TvGuideModule::class
    ]
)
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin<App> {
            androidContext(this@App)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}