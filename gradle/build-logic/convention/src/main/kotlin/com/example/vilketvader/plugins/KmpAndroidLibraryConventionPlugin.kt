package com.example.vilketvader.plugins

import com.android.build.gradle.LibraryExtension
import com.example.vilketvader.configureAndroid
import com.example.vilketvader.configureDetekt
import com.example.vilketvader.configureSpotless
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpAndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("com.android.library")
                apply("org.gradle.android.cache-fix")
                apply("io.gitlab.arturbosch.detekt")
            }

            configureSpotless()

            extensions.configure<LibraryExtension> {
                configureAndroid()
                defaultConfig.targetSdk = 34
                testOptions.animationsDisabled = true
            }

            configureDetekt(extensions.getByType<DetektExtension>())

            extensions.configure<KotlinMultiplatformExtension> {
                // Android target
                androidTarget {
                    compilations.all {
                        kotlinOptions {
                            jvmTarget = "17"
                        }
                    }
                }

                // iOS targets
                listOf(
                    iosX64(),
                    iosArm64(),
                    iosSimulatorArm64()
                ).forEach { iosTarget ->
                    iosTarget.binaries.framework {
                        baseName = project.name
                        isStatic = true
                    }
                }

                // Configure common compiler options
                sourceSets.configureEach {
                    languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                }
            }
        }
    }
}
