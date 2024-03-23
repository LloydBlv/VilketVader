plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}
kotlin { jvmToolchain(17) }
dependencies {
    implementation(libs.kotlinx.coroutines.jvm)
    implementation(libs.javax.inject)
    implementation(libs.kermit)
}