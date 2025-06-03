plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.vanotech.experiments.feature.camera"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        minSdk = rootProject.extra["minSdk"] as Int

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = rootProject.extra["sourceCompatibility"] as JavaVersion
        targetCompatibility = rootProject.extra["targetCompatibility"] as JavaVersion
    }
    kotlinOptions {
        jvmTarget = rootProject.extra["jvmTarget"] as String
    }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:utils"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling)

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.compose)
    implementation(libs.androidx.camera.lifecycle)

    val coilBom = platform(libs.coil.bom)
    implementation(coilBom)
    implementation(libs.coil.compose)

    implementation(libs.accompanist.permissions)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(libs.kotlinx.serialization.json)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}