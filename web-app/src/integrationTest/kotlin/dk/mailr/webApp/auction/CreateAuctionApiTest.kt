package dk.mailr.webApp.auction

import dk.mailr.webApp.BaseApiTest
import dk.mailr.webApp.module
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.junit.jupiter.api.Test

class CreateAuctionApiTest : BaseApiTest() {
    @Test
    fun `should be possible to create auction`() {
        withTestApplication({ module(DB_CONTAINER.replicaSetUrl) }) {
            handleRequest(HttpMethod.Post, "/auction/create-auction").apply {
                response.status() shouldBe HttpStatusCode.OK
                response.content.shouldNotBeNull()
            }
        }
    }
}
