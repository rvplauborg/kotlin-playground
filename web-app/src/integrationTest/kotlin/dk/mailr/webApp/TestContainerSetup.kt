package dk.mailr.webApp

import org.testcontainers.containers.MongoDBContainer

object TestContainerSetup {
    val DB_CONTAINER = MongoDBContainer("mongo:4.4.8")

    init {
        DB_CONTAINER.start()
    }
}
