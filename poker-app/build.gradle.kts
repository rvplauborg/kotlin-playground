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
    val ktorVersion: String by project
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    val logbackVersion: String by project
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
}
