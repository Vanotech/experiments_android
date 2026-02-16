package com.vanotech.experiments.data.media

import com.vanotech.experiments.data.media.internal.DefaultMediaRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [MediaModule.Bindings::class])
@InstallIn(SingletonComponent::class)
object MediaModule {

    @Module
    @InstallIn(SingletonComponent::class)
    internal interface Bindings {
        @Binds
        fun bindsEventRepo(mediaRepo: DefaultMediaRepo): MediaRepo
    }
}