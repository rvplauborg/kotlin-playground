plugins {
    id("vertical.template.kotlin-common-conventions")
}

dependencies {
    api(libs.com.trendyol.kediatr.core.get())
    api(libs.org.litote.kmongo.kmongo.coroutine.get())
    api(libs.io.insert.koin.koin.core.get())

    implementation(libs.com.fasterxml.jackson.datatype.jackson.datatype.jsr310.get()) // must be here for entity serialization to wor.get()k

    testFixturesImplementation(libs.org.testcontainers.mongodb.get())
    testFixturesImplementation(libs.org.testcontainers.junit.jupiter.get())
    testFixturesApi(libs.com.appmattus.fixture.get())
}
