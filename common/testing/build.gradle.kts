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
                "proguard-rules.pro",
            )
        }
    }
}

dependencies {
    implementation(libs.kotlinx.serialization.json.jvm)
    implementation(libs.kotlinx.coroutines.jvm)
    implementation(libs.assertk)
    implementation(libs.timber)
    implementation(projects.libs.data)
    implementation(projects.libs.domain)
    implementation("com.squareup.okhttp3:mockwebserver:4.12.0")

}
