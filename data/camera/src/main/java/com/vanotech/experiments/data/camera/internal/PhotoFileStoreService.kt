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
internal class PhotoFileStoreService @Inject internal constructor(
    @ApplicationContext context: Context
) : RecentFileStore(
    parentDir = File(context.filesDir, DIRECTORY_NAME),
    filePrefix = FILE_NAME,
    fileSuffix = FILE_EXT
) {
    val photo: Flow<File?> = files.map {
        it.firstOrNull()
    }

    companion object {
        private const val DIRECTORY_NAME = "photo"
        private const val FILE_NAME = "photo"
        private const val FILE_EXT = ".image"
    }
}