plugins {
    id(libs.plugins.com.github.benmanes.versions.get().pluginId)
    alias(libs.plugins.nl.littlerobots.version.catalog.update)
    alias(libs.plugins.org.jetbrains.kover)
}

repositories {
    mavenCentral()
}
