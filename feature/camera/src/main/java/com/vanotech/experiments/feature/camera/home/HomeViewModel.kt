package com.vanotech.experiments.feature.camera.home

import androidx.lifecycle.ViewModel
import com.vanotech.experiments.data.camera.CaptureRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    captureRepo: CaptureRepo
) : ViewModel() {
    val capture = captureRepo.capture
}