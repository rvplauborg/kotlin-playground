/*
 * Detailed information about configuring a multi-project build in Gradle can be found
 * in the user manual at https://docs.gradle.org/7.2/userguide/multi_project_builds.html
 */

rootProject.name = "vertical-template"

dependencyResolutionManagement {
    // Default repository setup that individual projects can override if needed
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
}

include("app")
include("user-access")
include("building-blocks")
// We might have to ensure we do not have projects that are named the same, i.e. :something:application and :otherthing:application
// as Gradle will fail. See https://github.com/gradle/gradle/issues/847 for more information.
