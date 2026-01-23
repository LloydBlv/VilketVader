plugins {
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ktorfitGradlePlugin)
    id("com.vilketvader.kmp.android.library")
    alias(libs.plugins.kspGradlePlugin)
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

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.libs.domain)
            implementation(libs.kotlinx.coroutines.jvm)
            implementation(libs.store)
            implementation(libs.ktorfitLib)
            implementation(libs.kotlinxDatetime)
            implementation(libs.kermit)
            implementation(libs.ktor.client.core)
            implementation(libs.ktorContentNegotiation)
            implementation(libs.ktorKotlinxSerialization)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinxCollectionsImmutable)
            implementation(libs.koin.core)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.assertk)
            implementation(libs.turbine)
            implementation(libs.ktor.clientmock)
        }

        androidMain.dependencies {
            implementation(libs.timber)
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.room.ktx)
            implementation(libs.ktorClientOkHttp)
            implementation(libs.okHttpLoggingInterceptor)
            implementation(libs.koin.android)
        }
    }
}

dependencies {
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspAndroid", libs.ktorfitKsp)
}
