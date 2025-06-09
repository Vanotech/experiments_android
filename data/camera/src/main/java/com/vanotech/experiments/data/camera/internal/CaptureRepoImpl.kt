package com.vanotech.experiments.data.camera.internal

import android.content.Context
import com.vanotech.experiments.data.camera.CaptureRepo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class CaptureRepoImpl @Inject internal constructor(
    @ApplicationContext private val context: Context
) : CaptureRepo {
    private val _captureFile = MutableSharedFlow<File>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val capture: Flow<File> = _captureFile

    init {
        val captureFile = getCaptureFile()
        _captureFile.tryEmit(captureFile)
    }

    private fun getCaptureFile(): File {
        val parentDir = File(context.filesDir, "camera")
        parentDir.mkdirs()
        return File(parentDir, "photo.jpg")
    }

    override suspend fun updateCapture(block: suspend (File) -> Unit) {
        val captureFile = getCaptureFile()
        block(captureFile)
        _captureFile.emit(captureFile)
    }
}