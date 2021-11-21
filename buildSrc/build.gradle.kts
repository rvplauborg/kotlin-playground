plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal() // so that external plugins can be resolved in dependencies section
    mavenCentral()
}

dependencies {
    // TODO would be great to deduplicate the versions across buildSrc and the actual projects & convention plugins
    val kotlinVersion = "1.6.0"
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.19.0-RC2")
    implementation("org.jetbrains.kotlinx:kover:0.4.2")
    implementation("org.jmailen.gradle:kotlinter-gradle:3.5.1")
}
