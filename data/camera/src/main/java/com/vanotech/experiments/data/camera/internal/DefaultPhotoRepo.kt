package com.vanotech.experiments.data.camera.internal

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.vanotech.experiments.data.camera.PhotoRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class DefaultPhotoRepo @Inject internal constructor(
    private val photoFileStoreService: PhotoFileStoreService
) : PhotoRepo {

    override val capture: Flow<Uri?> = photoFileStoreService.photo.map {
        it?.toUri()
    }

    override suspend fun setPhoto(source: File) {
        photoFileStoreService.insert(source)
    }

    override suspend fun setPhoto(context: Context, source: Uri) {
        photoFileStoreService.insert(context, source)
    }
}