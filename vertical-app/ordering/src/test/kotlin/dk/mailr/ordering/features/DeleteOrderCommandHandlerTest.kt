package dk.mailr.ordering.features

import dk.mailr.buildingblocks.exceptions.NotFoundException
import dk.mailr.buildingblocks.fakes.FakeUnitOfWork
import dk.mailr.buildingblocks.fakes.fixture
import dk.mailr.ordering.FakeOrderRepository
import dk.mailr.ordering.OrderFixtures
import dk.mailr.ordering.domain.events.OrderDeletedEvent
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldExist
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.util.UUID

internal class DeleteOrderCommandHandlerTest {
    private val fakeOrderRepository = spyk(FakeOrderRepository())

    private val handler = DeleteOrderCommandHandler(fakeOrderRepository, FakeUnitOfWork())

    @Test
    fun `should be possible to delete existing order`() = runBlocking<Unit> {
        val entity = OrderFixtures.newOrder()
        fakeOrderRepository.save(entity)

        handler.handleAsync(DeleteOrderCommand.of(UUID.fromString(entity._id.value)))

        verify { fakeOrderRepository.save(any()) }
        fakeOrderRepository.publishedEvents shouldExist { it is OrderDeletedEvent }
    }

    @Test
    fun `should fail if no order exists`() = runBlocking<Unit> {
        shouldThrow<NotFoundException> { handler.handleAsync(DeleteOrderCommand.of(fixture())) }
    }
}
