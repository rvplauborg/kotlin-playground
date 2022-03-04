package dk.mailr.webApp

import org.testcontainers.containers.MongoDBContainer

abstract class BaseApiTest {
    companion object {
        val DB_CONTAINER = MongoDBContainer("mongo:4.4.8")

        init {
            DB_CONTAINER.start()
        }
    }
}
