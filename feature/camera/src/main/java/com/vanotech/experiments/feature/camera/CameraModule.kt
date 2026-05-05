package com.vanotech.experiments.feature.camera

import com.vanotech.experiments.data.camera.CameraDataModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [CameraDataModule::class])
@ComponentScan
class CameraModule