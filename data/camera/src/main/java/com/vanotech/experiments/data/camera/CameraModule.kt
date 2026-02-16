package com.vanotech.experiments.data.camera

import com.vanotech.experiments.data.camera.internal.DefaultPhotoRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [CameraModule.Bindings::class])
@InstallIn(SingletonComponent::class)
object CameraModule {

    @Module
    @InstallIn(SingletonComponent::class)
    internal interface Bindings {
        @Binds
        fun bindsPhotoRepo(photoRepo: DefaultPhotoRepo): PhotoRepo
    }
}