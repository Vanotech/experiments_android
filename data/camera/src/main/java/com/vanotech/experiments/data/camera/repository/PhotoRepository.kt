package com.vanotech.experiments.data.camera.repository

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PhotoRepository {
    val photo: Flow<Uri?>

    suspend fun setPhoto(source: File)

    suspend fun setPhoto(context: Context, source: Uri)
}