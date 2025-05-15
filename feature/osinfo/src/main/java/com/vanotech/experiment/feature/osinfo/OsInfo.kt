package com.vanotech.experiment.feature.osinfo

import android.os.Build

internal object OsInfo {
    fun getApiLevel(): String = Build.VERSION.SDK_INT.toString()

    fun getCodeName(): String = when (Build.VERSION.SDK_INT) {
        Build.VERSION_CODES.N -> "Nougat"
        Build.VERSION_CODES.N_MR1 -> "Nougat"
        Build.VERSION_CODES.O -> "Oreo"
        Build.VERSION_CODES.O_MR1 -> "Oreo"
        Build.VERSION_CODES.P -> "Pie"
        Build.VERSION_CODES.Q -> "Q"
        Build.VERSION_CODES.R -> "R"
        Build.VERSION_CODES.S -> "S"
        Build.VERSION_CODES.TIRAMISU -> "Tiramisu"
        Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> "Upside Down Cake"
        Build.VERSION_CODES.VANILLA_ICE_CREAM -> "Vanilla Ice Cream"
        else -> Build.VERSION.CODENAME
    }

    fun getVersion(): String = Build.VERSION.RELEASE
}