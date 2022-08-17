group = "dk.mailr"

// Workaround for Gradle issue, see https://github.com/gradle/gradle/issues/847#issuecomment-1159365667
subprojects {
    val subGroup = path.replace(':', '.').replace('-', '_')
    group = "${rootProject.group}$subGroup"
}

plugins {
    id(libs.plugins.com.github.benmanes.versions.get().pluginId)
    alias(libs.plugins.nl.littlerobots.version.catalog.update)
    alias(libs.plugins.org.jetbrains.kover)
}

repositories {
    mavenCentral()
}
