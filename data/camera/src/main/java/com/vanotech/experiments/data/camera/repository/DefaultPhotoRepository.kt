package com.vanotech.experiments.data.camera.repository

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.vanotech.experiments.data.camera.local.PhotoFileStoreService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory
import java.io.File


@Factory
internal class DefaultPhotoRepository(
    private val photoFileStoreService: PhotoFileStoreService
) : PhotoRepository {

    override val photo: Flow<Uri?> = photoFileStoreService.photo.map {
        it?.toUri()
    }

    override suspend fun setPhoto(source: File) {
        photoFileStoreService.insert(source)
    }

    override suspend fun setPhoto(context: Context, source: Uri) {
        photoFileStoreService.insert(context, source)
    }
}
