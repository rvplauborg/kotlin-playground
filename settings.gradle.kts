/*
 * Detailed information about configuring a multi-project build in Gradle can be found
 * in the user manual at https://docs.gradle.org/7.2/userguide/multi_project_builds.html
 */

rootProject.name = "kotlin-playground"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    // Default repository setup that individual projects can override if needed
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
}

// We might have to ensure we do not have projects that are named the same, i.e. :something:application and :otherthing:application
// as Gradle will fail. See https://github.com/gradle/gradle/issues/847 for more information.
include("building-blocks")
include("web-app")
include("clean-app:auction:auction-application")
include("clean-app:auction:auction-domain")
include("clean-app:auction:auction-infrastructure")
include("vertical-app:ordering")
include("desktop-app")
