plugins {
    id("vertical.template.kotlin-common-conventions")
}

dependencies {
    implementation(projects.buildingBlocks)

    testImplementation(testFixtures(projects.buildingBlocks))

    testFixturesImplementation(testFixtures(projects.buildingBlocks))
}
