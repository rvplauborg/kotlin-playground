plugins {
    id("vertical.template.kotlin-application-conventions")
}

version = "0.0.1"
application {
    mainClass.set("dk.mailr.pokerApp.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":building-blocks"))
    val ktorVersion: String by project
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    val logbackVersion: String by project
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    val koinVersion = "3.1.3"
    implementation("io.insert-koin:koin-ktor:$koinVersion")

    testImplementation("io.insert-koin:koin-test-junit5:$koinVersion") {
        exclude("org.jetbrains.kotlin", "kotlin-test-junit")
    }
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
}
