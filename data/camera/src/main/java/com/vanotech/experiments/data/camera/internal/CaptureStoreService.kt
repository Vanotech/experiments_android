package com.vanotech.experiments.data.camera.internal

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * manages capture file
 * only newest file is kept
 *
 * addresses image loader caching/reload issues by using new "unique" filenames
 */
@Singleton
internal class CaptureStoreService @Inject internal constructor(
    @ApplicationContext private val context: Context
) {
    private val parentDir = File(context.filesDir, CAPTURE_DIRECTORY_NAME)
    private val fileNameRegex = Regex("${CAPTURE_FILE_NAME}_\\d+\\.${CAPTURE_FILE_EXT}")

    private val _capture = MutableStateFlow<File?>(null)
    val capture: Flow<File?> = _capture

    init {
        sync()
    }

    private fun newFile(): File {
        parentDir.mkdirs()

        val timestamp = System.currentTimeMillis()
        val fileName = "${CAPTURE_FILE_NAME}_${timestamp}.$CAPTURE_FILE_EXT"
        return File(parentDir, fileName)
    }

    private fun sync() {
        parentDir.mkdirs()

        val files = parentDir.listFiles().orEmpty()

        val captureFiles = files
            .filter { fileNameRegex.matches(it.name) }
            .sortedDescending()

        val newestCaptureFile = captureFiles.firstOrNull()
        _capture.tryEmit(newestCaptureFile)

        val oldCaptureFiles = captureFiles.drop(1)
        oldCaptureFiles.forEach {
            it.delete()
        }
    }

    private suspend fun update(block: suspend (File) -> Unit) {
        val capture = newFile()
        block(capture)
        sync()
    }

    suspend fun update(inputStream: InputStream) {
        update { target ->
            target.outputStream().use { output ->
                inputStream.copyTo(output)
            }
        }
    }

    suspend fun update(file: File) {
        file.inputStream().use { input ->
            update(input)
        }
    }

    suspend fun update(uri: Uri) {
        context.contentResolver.openInputStream(uri)?.use { input ->
            update(input)
        }
    }

    companion object {
        private const val CAPTURE_DIRECTORY_NAME = "capture"
        private const val CAPTURE_FILE_NAME = "capture"
        private const val CAPTURE_FILE_EXT = "image"
    }
}