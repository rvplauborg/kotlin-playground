package dk.mailr.auctionApplication

import dk.mailr.auctionDomain.events.AuctionCreatedEvent
import dk.mailr.buildingblocks.fakes.FakeUnitOfWork
import dk.mailr.buildingblocks.fakes.fixture
import io.kotest.matchers.collections.shouldExist
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class CreateAuctionCommandAsyncHandlerTest {
    private val fakeAuctionRepository = spyk(FakeAuctionRepository())
    private val fakeUnitOfWork = spyk(FakeUnitOfWork())

    private val handler = CreateAuctionCommandAsyncHandler(fakeAuctionRepository, fakeUnitOfWork)

    @Test
    fun `should create auction when valid input`() = runTest {
        val command = fixture<CreateAuctionCommand>()

        handler.handleAsync(command)

        fakeAuctionRepository.publishedEvents shouldExist { it is AuctionCreatedEvent }
        coVerify { fakeAuctionRepository.save(any()) }
        coVerify { fakeUnitOfWork.inTransactionAsync(any()) }
    }
}
