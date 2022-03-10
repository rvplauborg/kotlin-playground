package dk.mailr.webApp.auction

import dk.mailr.auctionApplication.GetAuctionQuery
import dk.mailr.auctionApplication.GetAuctionQueryResponse
import dk.mailr.webApp.shouldHaveContentEqualTo
import dk.mailr.webApp.shouldHaveStatus
import dk.mailr.webApp.withApiTestApplication
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.mockk.coEvery
import org.junit.jupiter.api.Test
import java.util.UUID

class GetAuctionApiTest {
    @Test
    fun `should be possible to get auction`() {
        withApiTestApplication { mediator, _ ->
            val auctionId = UUID.randomUUID()
            val expectedResponse = GetAuctionQueryResponse(auctionId.toString(), "name")
            coEvery { mediator.executeQueryAsync(any<GetAuctionQuery>()) } returns expectedResponse

            with(handleRequest(HttpMethod.Get, "/auction/get-auction/$auctionId")) {
                response shouldHaveStatus HttpStatusCode.OK
                response shouldHaveContentEqualTo expectedResponse
            }
        }
    }
}
