plugins {
    id("kotlin.playground.kotlin-common-conventions")
}

dependencies {
    implementation(projects.buildingBlocks)
    implementation(libs.io.ktor.ktor.server.core)
    implementation(libs.io.insert.koin.koin.ktor)
    implementation(libs.io.ktor.ktor.html.builder)

    testImplementation(testFixtures(projects.buildingBlocks))
    testImplementation(testFixtures(projects.verticalApp.ordering))

    testFixturesImplementation(testFixtures(projects.buildingBlocks))
}
