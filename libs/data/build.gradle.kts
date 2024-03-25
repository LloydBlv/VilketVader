plugins {
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ktorfitGradlePlugin)
    id("com.vilketvader.kotlin.android")
    id("com.vilketvader.android.library")
    id("com.vilketvader.hilt")
}

ksp {
    allWarningsAsErrors = true
}

android {
    namespace = "com.example.data"
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
    implementation(projects.libs.domain)
    testImplementation(projects.common.testing)
    implementation(libs.kotlinx.coroutines.jvm)
    implementation(libs.store)
    implementation(libs.timber)
    testImplementation(libs.robolectric)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    kspTest(libs.androidx.room.compiler)

    debugImplementation("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:4.0.0")

    implementation(libs.ktorfitLib)
    implementation(libs.kotlinxDatetime)
    implementation(libs.kermit)
    implementation(libs.ktorClientOkHttp)
    implementation(libs.okHttpLoggingInterceptor)
    implementation(libs.ktorContentNegotiation)
    implementation(libs.ktorKotlinxSerialization)
    testImplementation(libs.ktor.clientmock)
    ksp(libs.ktorfitKsp)
    kspTest(libs.ktorfitKsp)
    testImplementation(libs.kotlinx.coroutines.test)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinxCollectionsImmutable)
    testImplementation(libs.junit)
    testImplementation(libs.assertk)
    testImplementation(libs.turbine)
}
