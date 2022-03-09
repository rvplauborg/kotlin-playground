package dk.mailr.webApp.auction

import dk.mailr.auctionApplication.CreateAuctionRequest
import dk.mailr.buildingblocks.fakes.fixture
import dk.mailr.buildingblocks.json.jsonMapper
import dk.mailr.webApp.TestContainerSetup
import dk.mailr.webApp.TestContainerSetup.DB_CONTAINER
import dk.mailr.webApp.apiTestModule
import dk.mailr.webApp.module
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.junit.jupiter.api.Test

class CreateAuctionApiTest {
    @Test
    fun `should be possible to create auction`() {
        withTestApplication({ apiTestModule() }) {
            with(handleRequest(HttpMethod.Post, "/auction/create-auction") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(jsonMapper.writeValueAsString(fixture<CreateAuctionRequest>()))
            }) {
                response.status() shouldBe HttpStatusCode.OK
                response.content.shouldNotBeNull()
            }
        }
    }
}
