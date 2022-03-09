package dk.mailr.webApp.auction

import dk.mailr.webApp.TestContainerSetup
import dk.mailr.webApp.TestContainerSetup.DB_CONTAINER
import dk.mailr.webApp.apiTestModule
import dk.mailr.webApp.module
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.junit.jupiter.api.Test

class GetAuctionApiTest {
    @Test
    fun `should be possible to get auction`() {
        withTestApplication({ apiTestModule() }) {
            val auctionId = createAuction()

            with(handleRequest(HttpMethod.Get, "/auction/get-auction/$auctionId")) {
                response.status() shouldBe HttpStatusCode.OK
                response.content.shouldNotBeNull()
            }
        }
    }
}
