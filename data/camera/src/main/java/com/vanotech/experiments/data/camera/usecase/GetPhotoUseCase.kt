package com.vanotech.experiments.data.camera.usecase

import android.net.Uri
import com.vanotech.experiments.data.camera.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class GetPhotoUseCase(
    photoRepository: PhotoRepository
) {
    val flow: Flow<Uri?> = photoRepository.photo
}