package com.vanotech.experiments.feature.media

import com.vanotech.experiments.data.media.MediaDataModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [MediaDataModule::class])
@ComponentScan
class MediaModule