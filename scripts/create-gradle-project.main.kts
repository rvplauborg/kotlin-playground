import java.io.File
import kotlin.system.exitProcess

println()

val targetDirectory: File = File(if (args.isEmpty()) ".." else args[0]).canonicalFile
println("Target directory: ${targetDirectory.path}")

print("Enter project name (snake-case): ")
val projectName = readLine()!!

if (projectName.lowercase() != projectName) {
    println("Project name must be snake-case. Try again.")
    exitProcess(-1)
}

val projectDirectory = targetDirectory.resolve(projectName)
val projectBuildFile = projectDirectory.resolve("build.gradle.kts")
if (!projectDirectory.mkdir() || !projectBuildFile.createNewFile()) {
    println("Failed to create direcory.")
    projectDirectory.deleteRecursively()
    exitProcess(-1)
}

projectBuildFile.appendText(
    """
    plugins {
        id("kotlin.playground.kotlin-common-conventions")
    }
    """.trimIndent()
)

val kotlinSourceDirectory = projectDirectory.resolve("src").resolve("main").resolve("kotlin")
if (!kotlinSourceDirectory.mkdirs()) {
    println("Failed to create kotlin src directory.")
    projectDirectory.deleteRecursively()
    exitProcess(-1)
}

val relativeRoot: String = projectDirectory.parentFile.name
val gradleSettingsFile = File("../settings.gradle.kts")
println(relativeRoot)
val rootProjectName: String = File("..").canonicalFile.name
if (relativeRoot == rootProjectName) {
    println("New outer project")
    gradleSettingsFile.appendText("include(\"$projectName\")\n")
} else {
    println("New nested project")
    gradleSettingsFile.appendText("include(\"$relativeRoot:$projectName\")\n")
}
println("Added project '$projectName' to settings.gradle.kts.")
