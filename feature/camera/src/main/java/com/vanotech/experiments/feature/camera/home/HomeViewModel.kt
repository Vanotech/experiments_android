package com.vanotech.experiments.feature.camera.home

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanotech.experiments.data.camera.CameraFileProvider
import com.vanotech.experiments.data.camera.CaptureRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val captureRepo: CaptureRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState(
        uri = captureRepo.capture
    ))
    val uiState: StateFlow<HomeUiState> = _uiState
    val uri = CameraFileProvider.getTempUri(context, CAPTURE_FILE_PREFIX, null)

    override fun onCleared() {
        CameraFileProvider.delete(context, uri)
        super.onCleared()
    }

    fun updatePhoto(uri: Uri) {
        viewModelScope.launch {
            try {
                captureRepo.setCapture(context, uri)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val CAPTURE_FILE_PREFIX = "capture"
    }
}