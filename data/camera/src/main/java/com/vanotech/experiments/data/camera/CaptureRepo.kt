package com.vanotech.experiments.data.camera

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.flow.Flow
import java.io.File

interface CaptureRepo {
    val capture: Flow<Uri?>

    suspend fun setCapture(source: File)

    suspend fun setCapture(context: Context, source: Uri)
}