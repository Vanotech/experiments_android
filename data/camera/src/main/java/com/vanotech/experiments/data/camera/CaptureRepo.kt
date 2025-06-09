package com.vanotech.experiments.data.camera

import kotlinx.coroutines.flow.Flow
import java.io.File

interface CaptureRepo {
    val capture: Flow<File>

    suspend fun updateCapture(block: suspend (File) -> Unit)
}