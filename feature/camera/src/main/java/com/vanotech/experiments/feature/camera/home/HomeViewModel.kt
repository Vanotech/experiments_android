package com.vanotech.experiments.feature.camera.home

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanotech.experiments.data.camera.CameraFileProvider
import com.vanotech.experiments.data.camera.PhotoRepo
import com.vanotech.experiments.data.camera.usecases.SetPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
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
    }
}