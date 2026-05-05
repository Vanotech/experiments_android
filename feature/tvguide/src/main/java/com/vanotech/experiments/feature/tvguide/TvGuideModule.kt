package com.vanotech.experiments.feature.tvguide

import com.vanotech.experiments.data.tvguide.TvGuideDataModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [TvGuideDataModule::class])
@ComponentScan
class TvGuideModule