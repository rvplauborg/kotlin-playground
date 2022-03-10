package dk.mailr.webApp.ordering.features;

import dk.mailr.buildingblocks.fakes.fixture
import dk.mailr.ordering.features.GetOrderQuery
import dk.mailr.ordering.features.GetOrderQueryResponseDTO
import dk.mailr.webApp.shouldHaveContentEqualTo
import dk.mailr.webApp.shouldHaveStatus
import dk.mailr.webApp.withApiTestApplication
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.mockk.coEvery
import java.util.UUID
import kotlin.test.Test

class GetOrderApiTest {
    @Test
    fun `should be possible to get an order`() {
        withApiTestApplication { mediator, _ ->
            val response = GetOrderQueryResponseDTO(fixture())
            coEvery { mediator.executeQueryAsync(any<GetOrderQuery>()) } returns response

            val call = handleRequest(HttpMethod.Get, "/ordering/orders/${UUID.randomUUID()}")

            call.response shouldHaveStatus HttpStatusCode.OK
            call.response shouldHaveContentEqualTo response
        }
    }
}
