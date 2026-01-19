package com.vanotech.experiments.data.camera

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PhotoRepo {
    val capture: Flow<Uri?>

    suspend fun setPhoto(source: File)

    suspend fun setPhoto(context: Context, source: Uri)
}