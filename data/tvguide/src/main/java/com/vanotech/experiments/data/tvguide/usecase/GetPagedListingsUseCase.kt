package com.vanotech.experiments.data.tvguide.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vanotech.experiments.data.tvguide.model.Listing
import com.vanotech.experiments.data.tvguide.repository.ListingRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class GetPagedListingsUseCase(
    private val listingRepository: ListingRepository
) {
    operator fun invoke(config: PagingConfig): Flow<PagingData<Listing>> {
        return listingRepository.getAllAsPagingData(config)
    }
}