package dk.mailr.webApp

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeNull
import kotlin.test.Test
import kotlin.test.assertNotNull

class CreateGameApiTest : BaseApiTest() {
    @Test
    fun `should be possible to create game`() {
        withTestApplication({ module(DB_CONTAINER.replicaSetUrl) }) {
            handleRequest(HttpMethod.Post, "/game/create-game").apply {
                response.status() shouldBeEqualTo HttpStatusCode.OK
                val actual = response.content
                assertNotNull(actual)
                response.content.shouldNotBeNull()
            }
        }
    }
}
