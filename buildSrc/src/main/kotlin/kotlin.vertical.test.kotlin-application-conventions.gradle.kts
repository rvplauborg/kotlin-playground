plugins {
    // Apply the common convention plugin for shared build configuration between library and application projects.
    id("kotlin.vertical.test.kotlin-common-conventions")

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}
