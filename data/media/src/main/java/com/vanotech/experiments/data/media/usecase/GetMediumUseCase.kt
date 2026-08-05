package com.vanotech.experiments.data.media.usecase

import com.vanotech.experiments.data.media.model.Media
import com.vanotech.experiments.data.media.repository.MediaRepository
import org.koin.core.annotation.Factory

@Factory
class GetMediumUseCase(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(mediaId: Int): Media? {
        return mediaRepository.get(mediaId)
    }
}