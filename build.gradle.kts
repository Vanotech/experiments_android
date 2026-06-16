buildscript {
    val compileSdk by extra(37)
    val minSdk by extra(26)
    val targetSdk by extra(37)
    val sourceCompatibility by extra(JavaVersion.VERSION_11)
    val targetCompatibility by extra(JavaVersion.VERSION_11)
    val jdkVersion by extra(11)
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.autonomousapps.dependency.analysis)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.koin.compiler) apply false
    alias(libs.plugins.kotlin.compose.compiler) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
}

dependencyAnalysis {
    structure {
        ignoreKtx(true)
    }
}