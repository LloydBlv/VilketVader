plugins {
    id("com.vilketvader.android.application")
    id("com.vilketvader.android.application.compose")
    id("com.vilketvader.kotlin.android")
    id("com.vilketvader.hilt")
    id("com.vilketvader.circuit.application")
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.example.vilketvader"
    defaultConfig {
        testInstrumentationRunner = "com.example.vilketvader.HiltTestRunner"
    }
    signingConfigs {
        create("release") {
            storeFile = file(System.getProperty("user.home") + "/.android/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.glance.appwidget)
    runtimeOnly(projects.libs.data)
    implementation(projects.libs.domain)
    implementation(libs.timber)
    implementation(projects.common.screens)
    implementation(projects.common.imageLoading)
    implementation(projects.ui.weather)
    implementation(projects.ui.home)
    implementation(projects.ui.widget)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.kotlinxDatetime)
    implementation(libs.kotlinxCollectionsImmutable)
    implementation(libs.ktorfitLib)
    implementation(libs.composeCoil)
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.icons)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(projects.common.testing)
    androidTestImplementation(projects.libs.data)
    androidTestImplementation(libs.androidx.room.ktx)
    androidTestImplementation(libs.androidx.uiautomator)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
