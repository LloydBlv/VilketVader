plugins {
    id("com.vilketvader.android.application")
    id("com.vilketvader.android.application.compose")
    id("com.vilketvader.kotlin.android")
    id("com.vilketvader.hilt")
    alias(libs.plugins.kotlin.parcelize)
}

ksp {
    arg("circuit.codegen.mode", "hilt")
}


android {
    namespace = "com.example.vilketvader"
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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
    runtimeOnly(projects.libs.data)
    implementation(projects.libs.domain)

    implementation(projects.common.screens)
    implementation(projects.ui.weather)
    implementation(projects.ui.home)

    implementation(libs.circuit.foundation)
    implementation(libs.circuit.runtime)
    implementation(libs.circuit.foundation)

    implementation(libs.kotlinx.serialization.json)



    implementation(libs.kotlinxDatetime)
    implementation(libs.kotlinxCollectionsImmutable)
    implementation(libs.ktorfitLib)
    implementation(libs.composeCoil)
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation(libs.androidx.core.ktx)
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
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}