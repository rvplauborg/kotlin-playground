package dk.mailr.webApp.auction

import dk.mailr.auctionApplication.CreateAuctionCommand
import dk.mailr.auctionApplication.CreateAuctionRequest
import dk.mailr.auctionApplication.CreateAuctionResponse
import dk.mailr.buildingblocks.fakes.fixture
import dk.mailr.buildingblocks.json.jsonMapper
import dk.mailr.webApp.shouldHaveContentEqualTo
import dk.mailr.webApp.shouldHaveStatus
import dk.mailr.webApp.withApiTestApplication
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.mockk.coVerify
import io.mockk.every
import org.junit.jupiter.api.Test
import java.util.UUID

class CreateAuctionApiTest {
    @Test
    fun `should be possible to create auction`() {
        withApiTestApplication { mediator, uuidGenerator ->
            val id = UUID.randomUUID()
            every { uuidGenerator.generate() } returns id

            val call = handleRequest(HttpMethod.Post, "/auction/create-auction") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(jsonMapper.writeValueAsString(fixture<CreateAuctionRequest>()))
            }

            call.response shouldHaveStatus HttpStatusCode.OK
            call.response shouldHaveContentEqualTo CreateAuctionResponse(id)
            coVerify { mediator.executeCommandAsync(any<CreateAuctionCommand>()) }
        }
    }
}
