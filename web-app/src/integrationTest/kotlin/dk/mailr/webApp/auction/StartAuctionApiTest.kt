package dk.mailr.webApp.auction

import dk.mailr.auctionApplication.StartAuctionRequest
import dk.mailr.buildingblocks.json.jsonMapper
import dk.mailr.webApp.BaseApiTest
import dk.mailr.webApp.module
import io.kotest.matchers.shouldBe
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.junit.jupiter.api.Test

class StartAuctionApiTest : BaseApiTest() {
    @Test
    fun `should be possible to create auction`() {
        withTestApplication({ module(DB_CONTAINER.replicaSetUrl) }) {
            val auctionId = createAuction()
            with(handleRequest(HttpMethod.Post, "/auction/start-auction") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(jsonMapper.writeValueAsString(StartAuctionRequest(auctionId)))
            }) {
                response.status() shouldBe HttpStatusCode.OK
            }
        }
    }
}
