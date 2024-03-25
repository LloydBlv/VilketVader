package com.example.vilketvader.plugins

import com.android.build.gradle.TestedExtension
import com.example.vilketvader.get
import com.example.vilketvader.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class RoborazziConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("io.github.takahirom.roborazzi")
            }
            extensions.getByType(TestedExtension::class.java).apply {
                testOptions.unitTests.all {
                    it.maxParallelForks = Runtime.getRuntime().availableProcessors()
                    it.useJUnit {
                        if (project.hasProperty("screenshot")) {
                            project.logger.lifecycle("include only screenshot tests")
                            includeCategories("com.example.testing.ScreenshotTesting")
                        }
                    }
                }
            }
            dependencies {
                "testImplementation"(libs["androidx-espresso-core"])
                "testImplementation"(libs["junit"])
                "testImplementation"(libs["robolectric"])
                "testImplementation"(libs["androidx-junit"])
                "testImplementation"(libs["roborazzi"])
                "testImplementation"(libs["roborazzi-rule"])
                "testImplementation"(libs["roborazzi-compose"])
            }
        }
    }
}