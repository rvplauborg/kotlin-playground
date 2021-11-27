package org.junit.runners.model

/**
 * Need fake class to be able to exclude JUnit4 dependencies from the project,
 * while still having a working Testcontainers setup.
 * See https://github.com/testcontainers/testcontainers-java/issues/970 for more information.
 */
@Suppress("unused")
open class Statement
