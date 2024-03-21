plugins {
    id("com.vilketvader.kotlin.android")
    id("com.vilketvader.android.library")
    id("com.vilketvader.hilt")
    id("com.vilketvader.android.library.compose")
}

android {
    namespace = "com.example.weather"
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(projects.libs.domain)
    implementation(projects.common.screens)
    implementation(libs.circuit.runtime.presenter)
    implementation(libs.circuit.retained)
    testImplementation(libs.circuit.test)
    testImplementation(libs.kotlinx.coroutines.test)

    implementation(libs.kotlinxCollectionsImmutable)


    testImplementation(libs.robolectric)
    testImplementation(libs.assertk)
    testImplementation(projects.common.testing)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}