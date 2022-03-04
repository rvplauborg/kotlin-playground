plugins {
    id("vertical.template.kotlin-common-conventions")
}

dependencies {
    implementation(projects.buildingBlocks)
    implementation(projects.cleanApp.auction.auctionApplication)
    implementation(projects.cleanApp.auction.auctionDomain)
    val ktorVersion: String by project
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    val koinVersion: String by project
    implementation("io.insert-koin:koin-ktor:$koinVersion")
}
