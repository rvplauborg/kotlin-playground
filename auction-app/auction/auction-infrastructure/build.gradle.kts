plugins {
    id("vertical.template.kotlin-common-conventions")
}

dependencies {
    implementation(projects.buildingBlocks)
    implementation(projects.auctionApp.auction.auctionApplication)
    implementation(projects.auctionApp.auction.auctionDomain)

    val koinVersion: String by project
    implementation("io.insert-koin:koin-ktor:$koinVersion")
}
