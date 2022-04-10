import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude

plugins {
    id("vertical.template.kotlin-application-conventions")
}

version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.buildingBlocks)
    implementation(projects.cleanApp.auction.auctionApplication)
    implementation(projects.cleanApp.auction.auctionInfrastructure)
    implementation(projects.verticalApp.ordering)
    implementation(libs.ch.qos.logback.logback.classic)
    implementation(libs.io.ktor.ktor.jackson)
    implementation(libs.com.fasterxml.jackson.datatype.jackson.datatype.jsr310)
    implementation(libs.com.fasterxml.jackson.module.jackson.module.kotlin)
    implementation(libs.io.ktor.ktor.server.netty)
    implementation(libs.io.insert.koin.koin.ktor)
    implementation(libs.io.insert.koin.koin.logger.slf4j)

    testImplementation(libs.io.insert.koin.koin.test.junit5) {
        exclude("org.jetbrains.kotlin", "kotlin-test-junit")
    }
    testImplementation(testFixtures(projects.buildingBlocks))

    integrationTestImplementation(libs.io.ktor.ktor.server.tests)
    integrationTestImplementation(libs.org.testcontainers.mongodb)
    integrationTestImplementation(libs.io.ktor.ktor.client.core)
    integrationTestImplementation(libs.io.ktor.ktor.client.cio)
    integrationTestImplementation(libs.io.ktor.ktor.client.jackson)
}
