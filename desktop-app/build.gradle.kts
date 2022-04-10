import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    alias(libs.plugins.org.jetbrains.compose)
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.allWarningsAsErrors = true
        kotlinOptions.jvmTarget = JavaLanguageVersion.of("16").toString()
        kotlinOptions.freeCompilerArgs += listOf(
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        )
    }
}

dependencies {
    implementation(compose.desktop.currentOs)

    implementation(libs.ch.qos.logback.logback.classic)
    implementation(libs.io.ktor.ktor.client.core)
    implementation(libs.io.ktor.ktor.client.cio)
    implementation(libs.io.ktor.ktor.client.jackson)
    implementation(libs.io.ktor.ktor.client.logging)
    implementation(libs.com.fasterxml.jackson.datatype.jackson.datatype.jsr310)

    implementation(projects.verticalApp.ordering)
}

compose.desktop {
    application {
        mainClass = "dk.mailr.desktopApp.MainKt"
    }
}
