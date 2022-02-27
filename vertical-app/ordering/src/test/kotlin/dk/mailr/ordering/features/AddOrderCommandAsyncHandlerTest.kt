package dk.mailr.ordering.features

import dk.mailr.buildingblocks.fakes.FakeUnitOfWork
import dk.mailr.ordering.FakeOrderRepository
import dk.mailr.ordering.OrderFixtures.Commands.addOrderCommand
import dk.mailr.ordering.domain.events.OrderCreatedEvent
import io.kotest.matchers.collections.shouldExist
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class AddOrderCommandAsyncHandlerTest {
    private val fakeOrderRepository = spyk(FakeOrderRepository())

    private val handler = AddOrderCommandAsyncHandler(fakeOrderRepository, FakeUnitOfWork())

    @Test
    fun `should be possible to add order`() = runBlocking<Unit> {

        handler.handleAsync(addOrderCommand)

        verify { fakeOrderRepository.save(any()) }
        fakeOrderRepository.publishedEvents shouldExist { it is OrderCreatedEvent }
    }
}