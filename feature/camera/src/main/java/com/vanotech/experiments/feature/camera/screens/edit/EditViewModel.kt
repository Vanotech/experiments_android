package com.vanotech.experiments.feature.camera.screens.edit

import android.content.Context
import androidx.lifecycle.ViewModel
import com.vanotech.experiments.data.camera.PhotoRepo
import com.vanotech.experiments.data.camera.usecases.SetPhotoUseCase
import org.koin.core.annotation.KoinViewModel
import java.io.File

@KoinViewModel
internal class EditViewModel(
    context: Context,
    private val photoRepo: PhotoRepo
) : ViewModel() {

    val captureFile = File.createTempFile(FILE_PREFIX, null, context.cacheDir)

    override fun onCleared() {
        captureFile.delete()
        super.onCleared()
    }

    suspend fun setPhoto(file: File) {
        val setPhotoUseCase = SetPhotoUseCase(photoRepo)
        setPhotoUseCase.execute(file)
    }

    companion object {
        private const val FILE_PREFIX = "photo"
    }
}