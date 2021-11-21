plugins {
    id("vertical.template.kotlin-common-conventions")
    `java-test-fixtures`
}

dependencies {
    val kediatrVersion: String by project
    api("com.trendyol:kediatr-core:$kediatrVersion")
    val fixtureVersion: String by project
    testFixturesImplementation("com.appmattus.fixture:fixture:$fixtureVersion")
}
