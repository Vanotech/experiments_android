package com.vanotech.experiments.core.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object PermissionUtils {
    fun isGranted(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun isGranted(context: Context, permissions: Array<String>): Boolean {
        return permissions.all { permission ->
            isGranted(context, permission)
        }
    }

    fun isGranted(permissions: Map<String, Boolean>): Boolean {
        return permissions.all { permission ->
            permission.value
        }
    }
}