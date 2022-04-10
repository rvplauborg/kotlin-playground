plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal() // so that external plugins can be resolved in dependencies section
    mavenCentral()
}

dependencies {
    implementation(libs.com.github.benmanes.versions.plugin)
    implementation(libs.org.jetbrains.kotlin.gradle.plugin)
    implementation(libs.io.gitlab.arturbosch.detekt.plugin)
    implementation(libs.org.jetbrains.kotlin.kover.plugin)
}
