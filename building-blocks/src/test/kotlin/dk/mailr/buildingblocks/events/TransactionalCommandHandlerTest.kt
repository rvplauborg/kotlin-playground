package dk.mailr.buildingblocks.events

import com.trendyol.kediatr.Command
import dk.mailr.buildingblocks.fakes.FakeUnitOfWork
import dk.mailr.buildingblocks.mediator.AsyncTransactionalCommandHandler
import io.mockk.spyk
import io.mockk.verifyOrder
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class TransactionalCommandHandlerTest {
    class TestCommand : Command

    private val fakeUnitOfWork = spyk(FakeUnitOfWork())

    private val transactionalCommandHandler: AsyncTransactionalCommandHandler<TestCommand> =
        object : AsyncTransactionalCommandHandler<TestCommand>(fakeUnitOfWork) {

            override suspend fun handleInTransaction(command: TestCommand) {
                fakeUnitOfWork.toString()
            }
        }

    @Test
    fun `should first start transaction, then call handler logic, then finally commit`() = runBlocking {
        transactionalCommandHandler.handleAsync(TestCommand())

        verifyOrder {
            fakeUnitOfWork.start()
            fakeUnitOfWork.toString()
            fakeUnitOfWork.commit()
        }
    }
}
