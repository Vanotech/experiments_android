package com.vanotech.experiments.feature.camera

import android.content.Context
import com.vanotech.experiments.core.utils.VersionedValue
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class CameraRepo @Inject internal constructor(
    @ApplicationContext private val context: Context
) {
    private val _captureFactory = VersionedValue.Factory<File>()

    private val _capture = MutableStateFlow(_captureFactory.newInstance(getCaptureFile()))
    val capture: StateFlow<VersionedValue<File>> = _capture

    fun getCaptureFile(): File {
        val parentDir = File(context.filesDir, "camera")
        parentDir.mkdirs()
        return File(parentDir, "photo.jpg")
    }

    fun notifyCaptureFileChanged() {
        _capture.value = _captureFactory.newInstance(getCaptureFile())
    }
}