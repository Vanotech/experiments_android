package com.vanotech.experiments.data.camera.internal

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
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
    private val _capture = MutableSharedFlow<Uri>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val capture: Flow<Uri> = _capture

    init {
        val captureUri = getCaptureUri()
        _capture.tryEmit(captureUri)
    }

    private fun getCaptureUri(): Uri {
        val parentDir = File(context.filesDir, CAPTURE_DIRECTORY_NAME)
        parentDir.mkdirs()
        val file = File(parentDir, CAPTURE_FILE_NAME)
        return file.toUri()
    }

    private suspend fun updateCapture(block: suspend (Uri) -> Unit) {
        val captureUri = getCaptureUri()
        block(captureUri)
        _capture.emit(captureUri)
    }

    override suspend fun updateCapture(source: File) {
        updateCapture { target ->
            source.inputStream().use { input ->
                context.contentResolver.openOutputStream(target)?.use { output ->
                    input.copyTo(output)
                }
            }
        }
    }

    override suspend fun updateCapture(source: Uri) {
        updateCapture { target ->
            context.contentResolver.openInputStream(source)?.use { input ->
                context.contentResolver.openOutputStream(target)?.use { output ->
                    input.copyTo(output)
                }
            }
        }
    }

    companion object {
        private const val CAPTURE_DIRECTORY_NAME = "capture"
        private const val CAPTURE_FILE_NAME = "capture.jpg"
    }
}