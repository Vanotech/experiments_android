buildscript {
    val compileSdk by extra(36)
    val minSdk by extra(26)
    val targetSdk by extra(36)
    val sourceCompatibility by extra(JavaVersion.VERSION_21)
    val targetCompatibility by extra(JavaVersion.VERSION_21)
    val jdkVersion by extra(21)
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
//    alias(libs.plugins.autonomousapps.dependency.analysis)

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose.compiler) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
}
