package dk.mailr.webApp.e2e

import dk.mailr.buildingblocks.fakes.fixture
import dk.mailr.ordering.features.AddOrderRequest
import dk.mailr.ordering.features.AddOrderResponse
import dk.mailr.ordering.features.GetOrderQueryResponseDTO
import io.kotest.matchers.shouldBe
import io.ktor.client.request.get
import io.ktor.client.request.post
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class OrderE2ETests : BaseE2ETest() {
    @Test
    fun `should be possible to get an added order`() = runTest {
        val addOrderResponse = httpClient.post<AddOrderResponse>("http://localhost:8081/ordering/add-order") {
            body = AddOrderRequest(fixture())
        }

        val getOrderResponse = httpClient.get<GetOrderQueryResponseDTO>("http://localhost:8081/ordering/order/${addOrderResponse.orderId}")

        getOrderResponse.order.id shouldBe addOrderResponse.orderId
    }
}
