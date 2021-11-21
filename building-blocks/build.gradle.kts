plugins {
    id("vertical.template.kotlin-common-conventions")
    `java-test-fixtures`
}

dependencies {
    api("com.trendyol:kediatr-core:1.0.17")
    val fixtureVersion: String by project
    testFixturesImplementation("com.appmattus.fixture:fixture:$fixtureVersion")
}
