plugins {
    id("kotlin.playground.kotlin-ktor-conventions")
}

dependencies {
    implementation(projects.buildingBlocks)
    implementation(projects.cleanApp.auction.auctionApplication)
    implementation(projects.cleanApp.auction.auctionDomain)
}
