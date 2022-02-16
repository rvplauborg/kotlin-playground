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
    implementation(projects.poker.pokerApplication)
    implementation(projects.poker.pokerInfrastructure)
    implementation(projects.auctionApp.auction.auctionApplication)
    implementation(projects.auctionApp.auction.auctionInfrastructure)
    val ktorVersion: String by project
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
