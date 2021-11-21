package dk.mailr.pokerApp

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

class ApplicationKtTest {
    @Test
    fun testRoot() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/").apply {
                response.status() shouldBeEqualTo HttpStatusCode.OK
                response.content shouldBeEqualTo "Hello, world!"
            }
        }
    }
}
