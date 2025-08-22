package com.vanotech.experiments.data.camera

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File


object CameraFileProvider {
    private fun authority(context: Context): String {
        val applicationId = context.packageName
        return "$applicationId.camera.provider"
    }

    fun delete(context: Context, uri: Uri) {
        context.contentResolver.delete(uri, null, null)
    }

    fun getTempUri(context: Context, prefix: String, suffix: String?): Uri {
        val authority = authority(context)
        val file = File.createTempFile(prefix, suffix, context.cacheDir)
        return FileProvider.getUriForFile(context, authority, file)
    }
}
