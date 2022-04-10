group = "dk.mailr"

plugins {
    id("vertical.template.kotlin-common-conventions")
}

// Unfortunately cannot use version catalog out of the box like in buildSrc/build.gradle.kts. See https://github.com/gradle/gradle/issues/15383
val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    implementation("io.ktor:ktor-server-core:${libs.findVersion("io-ktor").get().requiredVersion}")
    implementation("io.insert-koin:koin-ktor:${libs.findVersion("io-insert-koin").get().requiredVersion}")
}
