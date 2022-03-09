plugins {
    id("vertical.template.kotlin-ktor-conventions")
}

dependencies {
    implementation(projects.buildingBlocks)
    implementation(projects.cleanApp.auction.auctionDomain)

    testImplementation(testFixtures(projects.buildingBlocks))
    testImplementation(testFixtures(projects.cleanApp.auction.auctionDomain))
}
