package com.example.vilketvader.plugins

import com.example.vilketvader.configureAndroid
import com.example.vilketvader.configureDetekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.gradle.android.cache-fix")
                apply("io.gitlab.arturbosch.detekt")
            }
            configureAndroid()
            configureDetekt(extensions.getByType<DetektExtension>())
        }
    }
}
