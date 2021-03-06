package dk.mailr.ordering.features

import dk.mailr.ordering.FakeOrderRepository
import dk.mailr.ordering.OrderFixtures
import dk.mailr.ordering.OrderFixtures.Queries.getOrdersQuery
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class GetOrdersQueryHandlerTest {
    private val fakeOrderRepository = spyk(FakeOrderRepository())

    private val handler = GetOrdersQueryHandler(fakeOrderRepository)

    @Test
    fun `should return empty list when no orders exist`() = runTest {
        val result = handler.handleAsync(getOrdersQuery)

        result.orders.shouldBeEmpty()
    }

    @Test
    fun `should return all orders when any orders exist`() = runTest {
        fakeOrderRepository.save(OrderFixtures.newOrder())
        fakeOrderRepository.save(OrderFixtures.newOrder())
        fakeOrderRepository.save(OrderFixtures.newOrder())

        val result = handler.handleAsync(getOrdersQuery)

        result.orders shouldHaveSize 3
    }
}
