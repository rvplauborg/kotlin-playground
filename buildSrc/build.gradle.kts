plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal() // so that external plugins can be resolved in dependencies section
    mavenCentral()
}

dependencies {
    val kotlinVersion = "1.6.0" // TODO would be great to deduplicate the kotlin version across buildSrc and the actual projects & convention plugins
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
    implementation("org.jmailen.gradle:kotlinter-gradle:3.5.1")
}
