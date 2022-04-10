plugins {
    id(libs.plugins.com.github.benmanes.versions.get().pluginId)
    alias(libs.plugins.nl.littlerobots.version.catalog.update)
}

repositories {
    mavenCentral()
}
