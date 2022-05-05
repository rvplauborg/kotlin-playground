import java.io.File
import kotlin.system.exitProcess

println("Add bounded context CLEAN arch. projects")

val targetDirectory: File = File(if (args.isEmpty()) ".." else args[0]).canonicalFile
println("Target directory: ${targetDirectory.path}")


print("Enter root project gradle name if any: ")
val rootGradleProjectName = readLine().orEmpty()

print("Enter project name (snake-case): ")
val projectName = readLine()!!

if (projectName.lowercase() != projectName) {
    println("Project name must be snake-case. Try again.")
    exitProcess(-1)
}

val outerProjectDir = targetDirectory.resolve(projectName)
if (!outerProjectDir.mkdir()) {
    println("Failed to create direcory $projectName.")
    outerProjectDir.deleteRecursively()
    exitProcess(-1)
}

val cleanProjectDirs = listOf("$projectName-application", "$projectName-domain", "$projectName-infrastructure")

cleanProjectDirs.forEach { subProjectName ->
    val projectDir = outerProjectDir.resolve(subProjectName)
    if (!projectDir.mkdir()) {
        println("Failed to create project dir $subProjectName.")
        outerProjectDir.deleteRecursively()
        exitProcess(-1)
    }

    val projectBuildFile = projectDir.resolve("build.gradle.kts")
    if (!projectBuildFile.createNewFile()) {
        println("Failed to create build file for $subProjectName.")
        outerProjectDir.deleteRecursively()
        exitProcess(-1)
    }

    projectBuildFile.appendText(
        """
    plugins {
        id("kotlin.playground.kotlin-common-conventions")
    }
    """.trimIndent()
    )

    val kotlinSourceDirectory = projectDir.resolve("src").resolve("main").resolve("kotlin")
    if (!kotlinSourceDirectory.mkdirs()) {
        println("Failed to create kotlin src directory.")
        outerProjectDir.deleteRecursively()
        exitProcess(-1)
    }

    val relativeRoot: String = projectDir.parentFile.name
    val gradleSettingsFile = File("../settings.gradle.kts")
    gradleSettingsFile.appendText("include(\"$rootGradleProjectName:$relativeRoot:$subProjectName\")\n")
    println("Added to settings.gradle.kts: include(\"$rootGradleProjectName:$relativeRoot:$subProjectName\")")
}
