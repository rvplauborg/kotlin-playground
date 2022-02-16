plugins {
    id("vertical.template.kotlin-common-conventions")
}

dependencies {
    implementation(projects.buildingBlocks)
    implementation(projects.auctionApp.auction.auctionApplication)
    implementation(projects.auctionApp.auction.auctionDomain)
}
