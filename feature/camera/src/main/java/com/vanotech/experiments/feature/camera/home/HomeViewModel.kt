package com.vanotech.experiments.feature.camera.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanotech.experiments.feature.camera.CameraRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val cameraRepo: CameraRepo
) : ViewModel() {
    val capture = cameraRepo.capture

    init {
        viewModelScope.launch {
            cameraRepo.notifyCaptureFileChanged()
        }
    }
}