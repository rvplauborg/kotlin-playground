import org.gradle.kotlin.dsl.provideDelegate

group = "dk.mailr"

plugins {
    id("vertical.template.kotlin-common-conventions")
}

dependencies {
    val ktorVersion: String by project
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    val koinVersion: String by project
    implementation("io.insert-koin:koin-ktor:$koinVersion")
}