package com.vanotech.experiments.feature.camera.edit

import android.content.Context
import android.util.Size
import androidx.camera.core.SurfaceRequest
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.vanotech.experiments.data.camera.CaptureRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
internal class EditViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val captureRepo: CaptureRepo
) : ViewModel() {

    private val camera = SimpleCamera()

    val surfaceRequest: StateFlow<SurfaceRequest?> = camera.surfaceRequest

    suspend fun bindToCamera(context: Context, lifecycleOwner: LifecycleOwner) {
        camera.bindToCamera(context, lifecycleOwner)
    }

    fun focusOnPoint(surfaceBounds: Size, x: Float, y: Float) {
        camera.focusOnPoint(surfaceBounds, x, y)
    }

    suspend fun switchCamera(context: Context, lifecycleOwner: LifecycleOwner) {
        camera.switchCamera(context, lifecycleOwner)
    }

    suspend fun takePhoto() {
        val captureFile = withContext(Dispatchers.IO) {
            File.createTempFile(CAPTURE_FILE_PREFIX, null, context.cacheDir)
        }
        try {
            camera.takePhoto(captureFile)
            captureRepo.setCapture(captureFile)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            captureFile.delete()
        }
    }

    companion object {
        private const val CAPTURE_FILE_PREFIX = "capture"
    }
}