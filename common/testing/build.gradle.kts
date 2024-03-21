plugins {
    id("com.vilketvader.kotlin.android")
    id("com.vilketvader.android.library")
}

android {
    namespace = "com.example.testing"
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
    implementation(libs.kotlinx.serialization.json.jvm)
    implementation(libs.assertk)
    implementation(projects.libs.data)
    implementation(projects.libs.domain)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}