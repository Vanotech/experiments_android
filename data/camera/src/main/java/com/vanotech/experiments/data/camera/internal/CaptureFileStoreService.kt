package com.vanotech.experiments.data.camera.internal

import android.content.Context
import com.vanotech.experiments.core.utils.RecentFileStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class CaptureFileStoreService @Inject internal constructor(
    @ApplicationContext context: Context
) : RecentFileStore(
    parentDir = File(context.filesDir, CAPTURE_DIRECTORY_NAME),
    filePrefix = CAPTURE_FILE_NAME,
    fileSuffix = CAPTURE_FILE_EXT
) {
    val capture: Flow<File?> = files.map {
        it.firstOrNull()
    }

    companion object {
        private const val CAPTURE_DIRECTORY_NAME = "capture"
        private const val CAPTURE_FILE_NAME = "capture"
        private const val CAPTURE_FILE_EXT = ".image"
    }
}