package dk.mailr.webApp.auction

import dk.mailr.auctionApplication.StartAuctionCommand
import dk.mailr.auctionApplication.StartAuctionRequest
import dk.mailr.buildingblocks.json.jsonMapper
import dk.mailr.webApp.withApiTestApplication
import io.kotest.matchers.shouldBe
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.mockk.coVerify
import org.junit.jupiter.api.Test
import java.util.UUID

class StartAuctionApiTest {
    @Test
    fun `should be possible to start auction`() {
        withApiTestApplication { mediator, _ ->
            val auctionId = UUID.randomUUID()

            val call = handleRequest(HttpMethod.Post, "/auction/start-auction") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(jsonMapper.writeValueAsString(StartAuctionRequest(auctionId)))
            }

            call.response.status() shouldBe HttpStatusCode.OK
            coVerify { mediator.executeCommandAsync(any<StartAuctionCommand>()) }
        }
    }
}
