package dk.mailr.webApp.auction

import dk.mailr.auctionApplication.CreateAuctionCommand
import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.webApp.BaseApiTest
import dk.mailr.webApp.module
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.koin.core.component.inject
import java.util.UUID

class GetAuctionApiTest : BaseApiTest() {
    @Test
    fun `should be possible to get auction`() {
        withTestApplication({ module(DB_CONTAINER.replicaSetUrl) }) {
            val mediator: Mediator by inject()
            val auctionId = UUID.randomUUID()
            runBlocking { mediator.executeCommandAsync(CreateAuctionCommand.of(auctionId, "auction")) }
            
            with(handleRequest(HttpMethod.Get, "/auction/get-auction/$auctionId")) {
                response.status() shouldBe HttpStatusCode.OK
                response.content.shouldNotBeNull()
            }
        }
    }
}
