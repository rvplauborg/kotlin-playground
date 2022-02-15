package dk.mailr.webApp

import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlin.test.Test
import kotlin.test.assertNotNull

class CreateGameApiTest : BaseApiTest() {
    @Test
    fun `should be possible to create game`() {
        withTestApplication({ module(DB_CONTAINER.replicaSetUrl) }) {
            handleRequest(HttpMethod.Post, "/game/create-game").apply {
                response.status() shouldBe HttpStatusCode.OK
                response.content.shouldNotBeNull()
            }
        }
    }
}
