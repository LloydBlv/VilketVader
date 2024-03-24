package com.example.vilketvader

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


internal fun Project.configureCircuit(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        dependencies {
            add("implementation", libs.findLibrary("circuit-runtime-presenter").get())
            add("implementation", libs.findLibrary("circuit-runtime-ui").get())
            add("implementation", libs.findLibrary("circuit-retained").get())
            add("implementation", libs.findLibrary("circuit-foundation").get())
            add("testImplementation", libs.findLibrary("circuit-test").get())
            add("api", libs.findLibrary("circuit-codegen-annotations").get())
            add("ksp", libs.findLibrary("circuit-codegen").get())
        }
    }
}