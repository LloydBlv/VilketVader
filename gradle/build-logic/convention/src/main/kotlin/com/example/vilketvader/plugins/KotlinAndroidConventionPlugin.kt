package com.example.vilketvader.plugins

import com.example.vilketvader.configureKotlin
import com.example.vilketvader.configureSpotless
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinAndroidConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.android")
            }

            configureSpotless()
            configureKotlin()
        }
    }
}
