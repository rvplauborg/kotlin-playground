plugins {
    id("vertical.template.kotlin-common-conventions")
}

dependencies {
    implementation(projects.buildingBlocks)
    val ktorVersion: String by project
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    val koinVersion: String by project
    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("io.ktor:ktor-html-builder:$ktorVersion")

    testImplementation(testFixtures(projects.buildingBlocks))
    testImplementation(testFixtures(projects.verticalApp.ordering))

    testFixturesImplementation(testFixtures(projects.buildingBlocks))
}
