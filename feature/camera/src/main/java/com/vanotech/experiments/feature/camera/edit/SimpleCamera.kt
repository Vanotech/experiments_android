package com.vanotech.experiments.feature.camera.edit

import android.content.Context
import android.util.Size
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceOrientedMeteringPointFactory
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class SimpleCamera {
    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest: StateFlow<SurfaceRequest?> = _surfaceRequest

    private var cameraControl: CameraControl? = null

    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private var surfaceMeteringPointFactory: SurfaceOrientedMeteringPointFactory? = null

    private val executor = Executors.newSingleThreadExecutor()

    private val imageCaptureUseCase = ImageCapture.Builder()
        .build()

    private val previewUseCase = Preview.Builder().build().apply {
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

        try {
            processCameraProvider.unbindAll()

            val camera = processCameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                imageCaptureUseCase,
                previewUseCase
            )
            cameraControl = camera.cameraControl

            cameraSelector = camera.cameraInfo.cameraSelector

            awaitCancellation()
        } finally {
            processCameraProvider.unbindAll()
            cameraControl = null
        }
    }


    fun focusOnPoint(surfaceBounds: Size, x: Float, y: Float) {
        val meteringPoint = surfaceMeteringPointFactory?.createPoint(x, y)
        meteringPoint?.also { point ->
            val meteringAction = FocusMeteringAction.Builder(point).build()
            cameraControl?.startFocusAndMetering(meteringAction)
        }
    }

    suspend fun switchCamera(context: Context, lifecycleOwner: LifecycleOwner) {
        cameraSelector = when (cameraSelector) {
            CameraSelector.DEFAULT_BACK_CAMERA -> CameraSelector.DEFAULT_FRONT_CAMERA
            else -> CameraSelector.DEFAULT_BACK_CAMERA
        }
        bindToCamera(context, lifecycleOwner)
    }

    suspend fun takePhoto(file: File) {
        suspendCoroutine { continuation ->
            val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()
            imageCaptureUseCase.takePicture(
                outputOptions,
                executor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        continuation.resume(file)
                    }

                    override fun onError(exception: ImageCaptureException) {
                        continuation.resumeWithException(exception)
                    }
                }
            )
        }
    }
}