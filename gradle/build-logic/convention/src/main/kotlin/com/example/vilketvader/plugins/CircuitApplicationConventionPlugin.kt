package com.example.vilketvader.plugins

import com.android.build.api.dsl.ApplicationExtension
import com.example.vilketvader.configureCircuit
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class CircuitApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.getByType<ApplicationExtension>()
            configureCircuit(extension)
        }
    }
}