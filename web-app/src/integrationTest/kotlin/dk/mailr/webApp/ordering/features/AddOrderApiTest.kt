package dk.mailr.webApp.ordering.features;

import dk.mailr.buildingblocks.fakes.fixture
import dk.mailr.buildingblocks.json.jsonMapper
import dk.mailr.ordering.features.AddOrderRequest
import dk.mailr.ordering.features.AddOrderResponse
import dk.mailr.webApp.shouldHaveContentEqualTo
import dk.mailr.webApp.shouldHaveStatus
import dk.mailr.webApp.withApiTestApplication
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.mockk.every
import java.util.UUID
import kotlin.test.Test

class AddOrderApiTest {
    @Test
    fun `should be possible to add an order`() {
        withApiTestApplication { _, uuidGenerator ->
            val id = UUID.randomUUID()
            every { uuidGenerator.generate() } returns id
            val request = fixture<AddOrderRequest>()

            val call = handleRequest(HttpMethod.Post, "/ordering/add-order") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(jsonMapper.writeValueAsString(request))
            }

            call.response shouldHaveStatus HttpStatusCode.OK
            call.response shouldHaveContentEqualTo AddOrderResponse(id.toString())
        }
    }
}
