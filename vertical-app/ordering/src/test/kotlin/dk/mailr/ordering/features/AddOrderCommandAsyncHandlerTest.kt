package dk.mailr.ordering.features

import dk.mailr.buildingblocks.fakes.FakeUnitOfWork
import dk.mailr.ordering.FakeOrderRepository
import dk.mailr.ordering.OrderFixtures
import dk.mailr.ordering.domain.events.OrderCreatedEvent
import io.kotest.matchers.collections.shouldExist
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class AddOrderCommandAsyncHandlerTest {
    private val fakeOrderRepository = spyk(FakeOrderRepository())
    private val fakeUnitOfWork = spyk(FakeUnitOfWork())

    private val handler = AddOrderCommandAsyncHandler(fakeOrderRepository, fakeUnitOfWork)

    @Test
    fun `should be possible to add order`() = runBlocking<Unit> {
        val command = OrderFixtures.Commands.addOrderCommand()

        handler.handleAsync(command)

        verify { fakeOrderRepository.save(any()) }
        fakeOrderRepository.publishedEvents shouldExist { it is OrderCreatedEvent }
    }
}
