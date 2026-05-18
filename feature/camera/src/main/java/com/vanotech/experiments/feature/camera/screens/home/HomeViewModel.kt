package com.vanotech.experiments.feature.camera.screens.home

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanotech.experiments.data.camera.CameraFileProvider
import com.vanotech.experiments.data.camera.PhotoRepo
import com.vanotech.experiments.data.camera.usecases.SetPhotoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
internal class HomeViewModel(
    private val context: Context,
    private val photoRepo: PhotoRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState
    val captureUri = CameraFileProvider.getTempUri(context, FILE_PREFIX, null)

    init {
        viewModelScope.launch {
            photoRepo.capture.collectLatest { uri ->
                _uiState.update { it.copy(uri = uri) }
            }
        }
    }

    override fun onCleared() {
        CameraFileProvider.delete(context, captureUri)
        super.onCleared()
    }

    suspend fun setPhoto(uri: Uri) {
        val setPhotoUseCase = SetPhotoUseCase(photoRepo)
        setPhotoUseCase.execute(context, uri)
    }

    companion object {
        private const val FILE_PREFIX = "photo"

        @Composable
        fun viewModel(): HomeViewModel {
            return koinViewModel()
        }
    }
}