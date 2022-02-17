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

class GetAuctionApiTest : BaseApiTest() {
    @Test
    fun `should be possible to get auction`() {
        withTestApplication({ module(DB_CONTAINER.replicaSetUrl) }) {
            val auctionId = createAuction()

            with(handleRequest(HttpMethod.Get, "/auction/get-auction/$auctionId")) {
                response.status() shouldBe HttpStatusCode.OK
                response.content.shouldNotBeNull()
            }
        }
    }
}
