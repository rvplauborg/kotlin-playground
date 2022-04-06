plugins {
    id("vertical.template.kotlin-common-conventions")
}

dependencies {
    val kediatrVersion: String by project
    api("com.trendyol:kediatr-core:$kediatrVersion")
    val kmongoVersion: String by project
    api("org.litote.kmongo:kmongo-coroutine:$kmongoVersion")
    val koinVersion: String by project
    api("io.insert-koin:koin-core:$koinVersion")

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:1.13.1") // must be here for entity serialization to work

    val testContainersVersion: String by project
    testFixturesImplementation("org.testcontainers:mongodb:$testContainersVersion")
    testFixturesImplementation("org.testcontainers:junit-jupiter:$testContainersVersion")
    val fixtureVersion: String by project
    testFixturesApi("com.appmattus.fixture:fixture:$fixtureVersion")
}
