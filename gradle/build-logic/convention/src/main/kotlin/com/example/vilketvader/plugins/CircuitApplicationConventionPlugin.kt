package com.example.vilketvader.plugins

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.internal.utils.KSP_PLUGIN_ID
import com.example.vilketvader.configureCircuit
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class CircuitApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(KSP_PLUGIN_ID)
            extensions.configure<KspExtension> { arg("circuit.codegen.mode", "hilt") }
            val extension = extensions.getByType<ApplicationExtension>()
            configureCircuit(extension)
        }
    }
}