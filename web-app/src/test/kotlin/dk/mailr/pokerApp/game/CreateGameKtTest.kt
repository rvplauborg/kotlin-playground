package dk.mailr.pokerApp.game

import dk.mailr.pokerApp.module
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.amshove.kluent.shouldBeEqualTo
import kotlin.test.Test

class CreateGameKtTest {
    @Test
    fun testPostGameCreate() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/game/create-game").apply {
                response.status() shouldBeEqualTo HttpStatusCode.OK
            }
        }
    }
}
