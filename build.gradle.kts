// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.vilketvader.root")
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.cacheFixPlugin) apply false
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.doctor)
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.roborazzi) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ktorfitGradlePlugin) apply false
    alias(libs.plugins.kspGradlePlugin) apply false
    alias(libs.plugins.dagger.hilt) apply false
}
