plugins {
    `kotlin-dsl`
    alias(libs.plugins.spotless)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

spotless {
    kotlin {
        target("src/**/*.kt")
        ktlint()
    }

    kotlinGradle {
        target("*.kts")
        ktlint()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.spotless.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.detekt.gradle)
    compileOnly(libs.roborazziGradlePlugin)
}

gradlePlugin {
    plugins {
        register("root") {
            id = "com.vilketvader.root"
            implementationClass = "com.example.vilketvader.plugins.RootConventionPlugin"
        }
        register("kotlinAndroid") {
            id = "com.vilketvader.kotlin.android"
            implementationClass = "com.example.vilketvader.plugins.KotlinAndroidConventionPlugin"
        }
        register("androidApplication") {
            id = "com.vilketvader.android.application"
            implementationClass = "com.example.vilketvader.plugins.AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "com.vilketvader.android.application.compose"
            implementationClass = "com.example.vilketvader.plugins.AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "com.vilketvader.android.library.compose"
            implementationClass = "com.example.vilketvader.plugins.AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "com.vilketvader.android.library"
            implementationClass = "com.example.vilketvader.plugins.AndroidLibraryConventionPlugin"
        }
        register("hiltAndroid") {
            id = "com.vilketvader.hilt"
            implementationClass = "com.example.vilketvader.plugins.HiltConventionPlugin"
        }
        register("circuitLibrary") {
            id = "com.vilketvader.circuit"
            implementationClass = "com.example.vilketvader.plugins.CircuitLibraryConventionPlugin"
        }
        register("circuitApplication") {
            id = "com.vilketvader.circuit.application"
            implementationClass = "com.example.vilketvader.plugins.CircuitApplicationConventionPlugin"
        }
        register("roborazzi") {
            id = "com.vilketvader.roborazzi"
            implementationClass = "com.example.vilketvader.plugins.RoborazziConventionPlugin"
        }
    }
}
