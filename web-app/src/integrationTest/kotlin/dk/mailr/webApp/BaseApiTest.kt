package dk.mailr.webApp

import org.koin.test.KoinTest
import org.testcontainers.containers.MongoDBContainer

abstract class BaseApiTest : KoinTest {
    companion object {
        val DB_CONTAINER = MongoDBContainer("mongo:4.4.8")

        init {
            DB_CONTAINER.start()
        }
    }
}
