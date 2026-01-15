package com.vanotech.experiments.data.media.internal

import androidx.paging.PagingData
import com.vanotech.experiments.data.media.Media
import com.vanotech.experiments.data.media.MediaRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

internal class MediaRepoImpl @Inject constructor(
) : MediaRepo {
    override suspend fun delete(item: Media) = TODO()

    override suspend fun get(id: Int) = MEDIA.find {
        id == it.id
    }

    override fun getAllAsPagingData(): Flow<PagingData<Media>> = flowOf(
        PagingData.from(MEDIA)
    )

    override suspend fun upsert(item: Media) = TODO()

    override suspend fun upsert(items: Collection<Media>) = TODO()

    companion object {
        private val MEDIA = listOf(
            Media(
                id = 1,
                url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                title = "Big Buck Bunny"
            ),
            Media(
                id = 2,
                url = "https://test-videos.co.uk/vids/jellyfish/mp4/h264/1080/Jellyfish_1080_10s_1MB.mp4",
                title = "Jellyfish"
            ),
            Media(
                id = 3,
                url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
                title = "Sintel"
            )
        )
    }
}