package dk.mailr.webApp.ordering.features;

import dk.mailr.buildingblocks.fakes.fixture
import dk.mailr.buildingblocks.json.jsonMapper
import dk.mailr.ordering.features.AddOrderRequest
import dk.mailr.webApp.apiTestModule
import io.kotest.matchers.shouldBe
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import kotlin.test.Test

class AddOrderRouteTest {
    @Test
    fun `it should be possible to add an order`() {
        withTestApplication({ apiTestModule() }) {
            with(handleRequest(HttpMethod.Post, "/ordering/add-order") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(jsonMapper.writeValueAsString(fixture<AddOrderRequest>()))
            }) {
                response.status() shouldBe HttpStatusCode.Found
            }
        }
    }
}
