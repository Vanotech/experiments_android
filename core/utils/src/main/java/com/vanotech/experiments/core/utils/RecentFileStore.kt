package com.vanotech.experiments.core.utils

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File
import java.io.InputStream

abstract class RecentFileStore(
    private val parentDir: File,
    private val filePrefix: String,
    private val fileSuffix: String,
    private val fileCount: Int = 1
) {
    private val fileNameRegex = Regex("${filePrefix}_\\d+\\Q${fileSuffix}\\E")

    private val _files = MutableStateFlow<List<File>>(emptyList())
    val files: Flow<List<File>> = _files

    init {
        sync()
    }

    private fun newFile(): File {
        parentDir.mkdirs()

        val timestamp = System.currentTimeMillis()
        val fileName = "${filePrefix}_$timestamp${fileSuffix}"
        return File(parentDir, fileName)
    }

    private fun sync() {
        parentDir.mkdirs()

        val files = parentDir.listFiles().orEmpty()

        val matchingFiles = files
            .filter { fileNameRegex.matches(it.name) }
            .sortedDescending()

        val newFiles = matchingFiles.take(fileCount)
        _files.tryEmit(newFiles)

        val oldFiles = matchingFiles.drop(fileCount)
        oldFiles.forEach {
            it.delete()
        }
    }

    suspend fun insert(block: suspend (File) -> Unit) {
        val target = newFile()
        block(target)
        sync()
    }

    suspend fun insert(inputStream: InputStream) {
        insert { target ->
            target.outputStream().use { output ->
                inputStream.copyTo(output)
            }
        }
    }

    suspend fun insert(file: File) {
        file.inputStream().use { input ->
            insert(input)
        }
    }

    suspend fun insert(context: Context, uri: Uri) {
        context.contentResolver.openInputStream(uri)?.use { input ->
            insert(input)
        }
    }
}