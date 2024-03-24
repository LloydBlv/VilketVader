plugins {
    id("com.vilketvader.kotlin.android")
    id("com.vilketvader.android.library")
    id("com.vilketvader.hilt")
    id("com.vilketvader.android.library.compose")
    id("com.vilketvader.circuit")
}

android.namespace = "com.example.weather"

dependencies {
    implementation(projects.libs.domain)

    implementation(projects.common.screens)
    implementation(projects.common.imageLoading)
    implementation(libs.timber)

    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.icons)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)


    testImplementation(libs.kotlinx.coroutines.test)

    implementation(libs.kotlinxCollectionsImmutable)


    testImplementation(libs.robolectric)
    testImplementation(libs.assertk)
    testImplementation(projects.common.testing)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}