plugins {
    id("vertical.template.kotlin-common-conventions")
    `java-test-fixtures`
}

dependencies {
    val kediatrVersion: String by project
    api("com.trendyol:kediatr-core:$kediatrVersion")
    api("org.litote.kmongo:kmongo-serialization:4.4.0")
    val logbackVersion: String by project
    api("ch.qos.logback:logback-classic:$logbackVersion")

    val testContainersVersion: String by project
    testFixturesImplementation("org.testcontainers:mongodb:$testContainersVersion")
    testFixturesImplementation("org.testcontainers:junit-jupiter:$testContainersVersion")
    val fixtureVersion: String by project
    testFixturesImplementation("com.appmattus.fixture:fixture:$fixtureVersion")
}
