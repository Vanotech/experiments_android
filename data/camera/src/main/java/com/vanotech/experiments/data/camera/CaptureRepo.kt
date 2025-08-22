package com.vanotech.experiments.data.camera

import android.graphics.Bitmap
import android.net.Uri
import kotlinx.coroutines.flow.Flow
import java.io.File

interface CaptureRepo {
    val capture: Flow<Uri?>

    suspend fun updateCapture(source: File)
    suspend fun updateCapture(source: Uri)
}