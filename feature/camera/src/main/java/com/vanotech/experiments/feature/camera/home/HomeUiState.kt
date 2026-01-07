package com.vanotech.experiments.feature.camera.home

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal data class HomeUiState(
    val uri: Flow<Uri?> = emptyFlow()
)