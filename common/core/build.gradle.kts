plugins {
    id("com.vilketvader.kmp.android.library")
}

android {
    namespace = "com.example.core"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinxDatetime)
        }
    }
}
