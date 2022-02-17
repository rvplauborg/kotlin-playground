plugins {
    id("vertical.template.kotlin-common-conventions")
    `java-test-fixtures`
}

dependencies {
    implementation(projects.buildingBlocks)

    testImplementation(testFixtures(projects.buildingBlocks))

    testFixturesImplementation(testFixtures(projects.buildingBlocks))
}
