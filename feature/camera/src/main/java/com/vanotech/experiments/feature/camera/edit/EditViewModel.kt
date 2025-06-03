package com.vanotech.experiments.feature.camera.edit

import android.content.Context
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceOrientedMeteringPointFactory
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanotech.experiments.feature.camera.CameraRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
internal class EditViewModel @Inject constructor(
    private val cameraRepo: CameraRepo
) : ViewModel() {
    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest: StateFlow<SurfaceRequest?> = _surfaceRequest

    private var surfaceMeteringPointFactory: SurfaceOrientedMeteringPointFactory? = null
    private var cameraControl: CameraControl? = null

    private val executor = Executors.newSingleThreadExecutor()

    private val imageCapture = ImageCapture.Builder()
        .build()

    private val preview = Preview.Builder().build().apply {
        setSurfaceProvider { newSurfaceRequest ->
            _surfaceRequest.update { newSurfaceRequest }
            surfaceMeteringPointFactory = SurfaceOrientedMeteringPointFactory(
                newSurfaceRequest.resolution.width.toFloat(),
                newSurfaceRequest.resolution.height.toFloat()
            )
        }
    }

    suspend fun bindToCamera(context: Context, lifecycleOwner: LifecycleOwner) {
        val processCameraProvider = ProcessCameraProvider.awaitInstance(context.applicationContext)
        val camera = processCameraProvider.bindToLifecycle(
            lifecycleOwner,
            DEFAULT_BACK_CAMERA,
            imageCapture,
            preview
        )
        cameraControl = camera.cameraControl

        try {
            awaitCancellation()
        } finally {
            processCameraProvider.unbindAll()
        }
    }

    fun focusOnPoint(offset: Offset) {
        val meteringPoint = surfaceMeteringPointFactory?.createPoint(offset.x, offset.y)
        meteringPoint?.also { point->
            val meteringAction = FocusMeteringAction.Builder(point).build()
            cameraControl?.startFocusAndMetering(meteringAction)
        }
    }

    fun takePhoto() {
        viewModelScope.launch {
            try {
                val captureFile = cameraRepo.getCaptureFile()
                val outputOptions = ImageCapture.OutputFileOptions.Builder(captureFile).build()
                suspendCoroutine { continuation ->
                    imageCapture.takePicture(
                        outputOptions,
                        executor,
                        object : ImageCapture.OnImageSavedCallback {
                            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                continuation.resume(Unit)
                            }

                            override fun onError(exception: ImageCaptureException) {
                                continuation.resumeWithException(exception)
                            }
                        }
                    )
                }
                cameraRepo.notifyCaptureFileChanged()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}