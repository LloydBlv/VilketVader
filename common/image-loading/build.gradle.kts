plugins {
    id("com.vilketvader.kotlin.android")
    id("com.vilketvader.android.library")
}

android.namespace = "com.example.imageloading"

dependencies {
    implementation(projects.libs.domain)
    api(libs.composeCoil)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
}