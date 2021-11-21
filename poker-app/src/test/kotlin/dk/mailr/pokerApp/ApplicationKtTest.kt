package dk.mailr.pokerApp

import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
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
