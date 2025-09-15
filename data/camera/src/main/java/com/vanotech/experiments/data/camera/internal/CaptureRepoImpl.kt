package com.vanotech.experiments.data.camera.internal

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.vanotech.experiments.data.camera.CaptureRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class CaptureRepoImpl @Inject internal constructor(
    private val captureStoreService: CaptureFileStoreService
) : CaptureRepo {

    override val capture: Flow<Uri?> = captureStoreService.capture.map {
        it?.toUri()
    }

    override suspend fun setCapture(source: File) {
        captureStoreService.insert(source)
    }

    override suspend fun setCapture(context: Context, source: Uri) {
        captureStoreService.insert(context, source)
    }
}