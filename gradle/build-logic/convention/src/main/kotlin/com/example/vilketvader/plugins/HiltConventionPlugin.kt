package com.example.vilketvader.plugins

import com.example.vilketvader.get
import com.example.vilketvader.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("dagger.hilt.android.plugin")
                apply("com.google.devtools.ksp")
            }
            dependencies {
                "implementation"(libs["hilt.android"])
                "ksp"(libs["hilt.android.compiler"])
                "testImplementation"(libs["hilt.android.testing"])
                "androidTestImplementation"(libs["hilt.android.testing"])
                "kspTest"(libs["hilt.android.testing"])
            }
        }
    }
}
