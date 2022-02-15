plugins {
    id("vertical.template.kotlin-common-conventions")
}

dependencies {
    implementation(projects.buildingBlocks)
    implementation(projects.auctionApp.auction.auctionDomain)
    val ktorVersion: String by project
    implementation("io.ktor:ktor-server-core:$ktorVersion")

    testImplementation(testFixtures(projects.buildingBlocks))
    integrationTestImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    integrationTestImplementation("org.testcontainers:mongodb:1.16.2")
}
