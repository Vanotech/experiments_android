package com.vanotech.experiments.data.camera.usecases

import android.content.Context
import android.net.Uri
import com.vanotech.experiments.data.camera.PhotoRepo
import org.koin.core.annotation.Factory
import java.io.File

class SetPhotoUseCase(
    private val photoRepo: PhotoRepo
) {
    suspend fun execute(file: File) {
        photoRepo.setPhoto(file)
    }

    suspend fun execute(context: Context, uri: Uri) {
        photoRepo.setPhoto(context, uri)
    }
}