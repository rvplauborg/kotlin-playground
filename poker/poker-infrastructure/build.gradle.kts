plugins {
    id("vertical.template.kotlin-common-conventions")
}

dependencies {
    implementation(projects.buildingBlocks)
    implementation(projects.poker.pokerApplication)
    implementation(projects.poker.pokerDomain)
}
