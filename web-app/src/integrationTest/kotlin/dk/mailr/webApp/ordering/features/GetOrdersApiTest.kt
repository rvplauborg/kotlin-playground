package dk.mailr.webApp.ordering.features

import dk.mailr.buildingblocks.fakes.fixture
import dk.mailr.ordering.features.GetOrdersQuery
import dk.mailr.ordering.features.GetOrdersQueryResponseDTO
import dk.mailr.webApp.shouldHaveContentEqualTo
import dk.mailr.webApp.shouldHaveStatus
import dk.mailr.webApp.withApiTestApplication
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.mockk.coEvery
import kotlin.test.Test

class GetOrdersApiTest {
    @Test
    fun `should be possible to get orders`() {
        withApiTestApplication { mediator, _ ->
            val response = GetOrdersQueryResponseDTO(fixture())
            coEvery { mediator.executeQueryAsync(any<GetOrdersQuery>()) } returns response

            val call = handleRequest(HttpMethod.Get, "/ordering/orders")

            call.response shouldHaveStatus HttpStatusCode.OK
            call.response shouldHaveContentEqualTo response
        }
    }
}
