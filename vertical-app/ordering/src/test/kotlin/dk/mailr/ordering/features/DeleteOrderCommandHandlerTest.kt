package dk.mailr.ordering.features

import dk.mailr.buildingblocks.exceptions.NotFoundException
import dk.mailr.buildingblocks.fakes.FakeUnitOfWork
import dk.mailr.buildingblocks.fakes.fixture
import dk.mailr.ordering.FakeOrderRepository
import dk.mailr.ordering.OrderFixtures
import dk.mailr.ordering.domain.events.OrderDeletedEvent
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldExist
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class DeleteOrderCommandHandlerTest {
    private val fakeOrderRepository = spyk(FakeOrderRepository())

    private val handler = DeleteOrderCommandHandler(fakeOrderRepository, FakeUnitOfWork())

    @Test
    fun `should be possible to delete existing order`() = runTest {
        val entity = OrderFixtures.newOrder()
        fakeOrderRepository.save(entity)

        handler.handleAsync(DeleteOrderCommand.of(entity._id.valueAsUUID()))

        coVerify { fakeOrderRepository.save(any()) }
        fakeOrderRepository.publishedEvents shouldExist { it is OrderDeletedEvent }
    }

    @Test
    fun `should fail if no order exists`() = runTest {
        shouldThrow<NotFoundException> { handler.handleAsync(DeleteOrderCommand.of(fixture())) }
    }
}
