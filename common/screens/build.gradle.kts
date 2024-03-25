plugins {
    id("com.vilketvader.kotlin.android")
    id("com.vilketvader.android.library")
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.example.screens"
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

dependencies {
    implementation(libs.circuit.runtime)
}
