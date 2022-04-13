import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "dk.mailr"

plugins {
    kotlin("jvm")
    id("io.gitlab.arturbosch.detekt")
    id("com.github.ben-manes.versions")
    `java-test-fixtures`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

kotlin {
    jvmToolchain {
        // See https://kotlinlang.org/docs/whatsnew1530.html#support-for-java-toolchains
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of("11"))
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
        kotlinOptions.jvmTarget = JavaLanguageVersion.of("11").toString()
        kotlinOptions.freeCompilerArgs += listOf(
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        )
    }
}

detekt {
    config = files(rootProject.layout.projectDirectory.file("detekt.yml"))
    buildUponDefaultConfig = true
}

// Unfortunately cannot use version catalog out of the box like in buildSrc/build.gradle.kts. See https://github.com/gradle/gradle/issues/15383
val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:${libs.findVersion("org-jetbrains-kotlin").get().requiredVersion}"))
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation("org.valiktor:valiktor-core:${libs.findVersion("org-valiktor").get().requiredVersion}")

    testImplementation(kotlin("test")) // will automatically include JUnit 5 based on our 'useJUnitPlatform' configuration for tasks.test
    testImplementation("io.kotest:kotest-runner-junit5:${libs.findVersion("io-kotest").get().requiredVersion}")
    testImplementation("io.kotest:kotest-assertions-core:${libs.findVersion("io-kotest").get().requiredVersion}")

    testImplementation("org.valiktor:valiktor-test:${libs.findVersion("org-valiktor").get().requiredVersion}")
    testImplementation(libs.findLibrary("com-appmattus-fixture").get())
    testImplementation(libs.findLibrary("io-mockk").get())
    testImplementation(libs.findLibrary("org-junit-jupiter-junit-jupiter").get())

    detektPlugins(libs.findLibrary("io-gitlab-arturbosch-detekt-detekt-formatting").get()) // ktlint wrapper for detekt
}
