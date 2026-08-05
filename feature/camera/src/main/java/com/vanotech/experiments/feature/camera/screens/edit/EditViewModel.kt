package com.vanotech.experiments.feature.camera.screens.edit

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.vanotech.experiments.data.camera.usecase.SetPhotoUseCase
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinViewModel
import java.io.File

@KoinViewModel
internal class EditViewModel(
    context: Context,
    private val setPhotoUseCase: SetPhotoUseCase
) : ViewModel() {

    val captureFile = File.createTempFile(FILE_PREFIX, null, context.cacheDir)

    override fun onCleared() {
        captureFile.delete()
        super.onCleared()
    }

    suspend fun setPhoto(file: File) {
        setPhotoUseCase(file)
    }

    companion object {
        private const val FILE_PREFIX = "photo"

        @Composable
        fun viewModel(): EditViewModel {
            return koinViewModel()
        }
    }
}