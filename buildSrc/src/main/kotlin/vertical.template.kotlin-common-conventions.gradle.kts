import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "dk.mailr"

plugins {
    kotlin("jvm")
    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.kotlinx.kover")
    `java-test-fixtures`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

kotlin {
    jvmToolchain {
        // See https://kotlinlang.org/docs/whatsnew1530.html#support-for-java-toolchains
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of("16"))
    }
}

configurations {
    all {
        exclude("junit", "junit") // to avoid use of JUnit4
    }
}

val integrationTest: SourceSet by sourceSets.creating {
    compileClasspath += sourceSets.main.get().output
    runtimeClasspath += sourceSets.main.get().output
}

val integrationTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.implementation.get(), configurations.testImplementation.get())
}

val integrationTestRuntimeOnly: Configuration by configurations.getting {
    extendsFrom(configurations.runtimeOnly.get(), configurations.testRuntimeOnly.get())
}

tasks {
    val testIntegration by creating(Test::class) {
        description = "Runs integration tests"
        group = "verification"

        testClassesDirs = integrationTest.output.classesDirs
        classpath = integrationTest.runtimeClasspath
        useJUnitPlatform()
        shouldRunAfter(test)
    }
    check {
        dependsOn(testIntegration)
    }
    test {
        useJUnitPlatform() // use JUnit 5, see https://kotlinlang.org/docs/gradle.html#set-dependencies-on-test-libraries
    }
    withType<KotlinCompile> {
        kotlinOptions.allWarningsAsErrors = true
        kotlinOptions.jvmTarget = JavaLanguageVersion.of("16").toString()
        kotlinOptions.freeCompilerArgs += listOf(
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        )
    }
    koverCollectReports {
        outputDir.set(rootProject.layout.buildDirectory.dir("my-reports"))
    }
}

detekt {
    config = files(rootProject.layout.projectDirectory.file("detekt.yml"))
    buildUponDefaultConfig = true
}

dependencies {
    val kotlinVersion: String by project
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:$kotlinVersion")) // use same version of kotlin library versions
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    val valiktorVersion: String by project
    implementation("org.valiktor:valiktor-core:$valiktorVersion")

    testImplementation(kotlin("test")) // will automatically include JUnit 5 based on our 'useJUnitPlatform' configuration for tasks.test
    val kotestVersion: String by project
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")

    testImplementation("org.valiktor:valiktor-test:$valiktorVersion")
    val fixtureVersion: String by project
    testImplementation("com.appmattus.fixture:fixture:$fixtureVersion")
    val mockkVersion: String by project
    testImplementation("io.mockk:mockk:$mockkVersion")
    val junitVersion: String by project
    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")

    val detektVersion: String by project
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion") // ktlint wrapper for detekt
}
