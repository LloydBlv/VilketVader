package com.example.vilketvader.plugins

import com.example.vilketvader.configureSpotless
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
            }

            configureSpotless()

            extensions.configure<KotlinMultiplatformExtension> {
                // JVM target for backend/desktop
                jvm()

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
                targets.configureEach {
                    compilations.configureEach {
                        compilerOptions.configure {
                            freeCompilerArgs.add("-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
                        }
                    }
                }

                sourceSets.configureEach {
                    languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                }
            }
        }
    }
}
