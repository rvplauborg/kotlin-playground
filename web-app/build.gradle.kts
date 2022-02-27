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
    val ktorVersion: String by project
    val logbackVersion: String by project
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-jackson:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    val koinVersion: String by project
    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")

    testImplementation("io.insert-koin:koin-test-junit5:$koinVersion") {
        exclude("org.jetbrains.kotlin", "kotlin-test-junit")
    }
    testImplementation(testFixtures(projects.buildingBlocks))

    integrationTestImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    integrationTestImplementation("org.testcontainers:mongodb:1.16.2")
}
