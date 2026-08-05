package com.vanotech.experiments.data.camera.usecase

import android.content.Context
import android.net.Uri
import com.vanotech.experiments.data.camera.repository.PhotoRepository
import org.koin.core.annotation.Factory
import java.io.File

@Factory
class SetPhotoUseCase(
    private val photoRepository: PhotoRepository
) {
    suspend operator fun invoke(file: File) {
        photoRepository.setPhoto(file)
    }

    suspend operator fun invoke(context: Context, uri: Uri) {
        photoRepository.setPhoto(context, uri)
    }
}