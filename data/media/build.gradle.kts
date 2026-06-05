plugins {
    alias(libs.plugins.autonomousapps.dependency.analysis)
    alias(libs.plugins.android.library)
    alias(libs.plugins.koin.compiler)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.vanotech.experiments.data.media"

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
    compileOptions {
        sourceCompatibility = rootProject.extra["sourceCompatibility"] as JavaVersion
        targetCompatibility = rootProject.extra["targetCompatibility"] as JavaVersion
    }
}

kotlin {
    val jdkVersion = rootProject.extra["jdkVersion"] as Int
    jvmToolchain(jdkVersion)
}


dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.bundles.koin.core)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}