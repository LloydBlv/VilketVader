plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.weather"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = "1.5.10"
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