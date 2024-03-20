plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ktorfitGradlePlugin)
    alias(libs.plugins.kspGradlePlugin)
}

android {
    namespace = "com.example.data"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(projects.libs.domain)
    testImplementation(projects.common.testing)
    implementation(libs.kotlinx.coroutines.jvm)
    implementation(libs.ktorfitLib)
    implementation(libs.kermit)
    implementation(libs.ktorClientOkHttp)
    implementation(libs.okHttpLoggingInterceptor)
    implementation(libs.ktorContentNegotiation)
    implementation(libs.ktorKotlinxSerialization)
    testImplementation(libs.ktorKotlinxSerialization)
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