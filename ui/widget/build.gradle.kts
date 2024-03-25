plugins {
    id("com.vilketvader.kotlin.android")
    id("com.vilketvader.android.library")
    id("com.vilketvader.hilt")
    id("com.vilketvader.android.library.compose")
}

android {
    namespace = "com.example.widget"

    lint {
        baseline = file("lint-baseline.xml")
    }
}

dependencies {
    implementation(projects.libs.domain)
    implementation(libs.composeCoil)

    implementation("androidx.glance:glance-material3:1.0.0")
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
