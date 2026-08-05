package com.vanotech.experiments.data.media.usecase

import androidx.paging.PagingData
import com.vanotech.experiments.data.media.model.Media
import com.vanotech.experiments.data.media.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class GetPagedMediaUseCase(
    mediaRepository: MediaRepository
) {
    val flow: Flow<PagingData<Media>> = mediaRepository.getAllAsPagingData()
}