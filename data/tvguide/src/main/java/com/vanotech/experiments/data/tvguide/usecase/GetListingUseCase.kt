package com.vanotech.experiments.data.tvguide.usecase

import com.vanotech.experiments.data.tvguide.model.Listing
import com.vanotech.experiments.data.tvguide.repository.ListingRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

@Factory
class GetListingUseCase(
    private val listingRepository: ListingRepository
) {
    suspend operator fun invoke(listingId: String): Flow<Listing?> {
        coroutineScope {
            launch {
                listingRepository.fetch(listingId)
            }
        }
        return listingRepository.getAsFlow(listingId)
    }
}