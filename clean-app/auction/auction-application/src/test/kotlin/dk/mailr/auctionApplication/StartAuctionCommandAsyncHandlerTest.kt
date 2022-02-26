package dk.mailr.auctionApplication

import dk.mailr.auctionDomain.AuctionFixtures
import dk.mailr.auctionDomain.events.AuctionStartedEvent
import dk.mailr.buildingblocks.fakes.FakeUnitOfWork
import io.kotest.matchers.collections.shouldExist
import io.mockk.coVerify
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class StartAuctionCommandAsyncHandlerTest {
    private val fakeAuctionRepository = spyk(FakeAuctionRepository())
    private val fakeUnitOfWork = spyk(FakeUnitOfWork())

    private val handler = StartAuctionCommandAsyncHandler(fakeAuctionRepository, fakeUnitOfWork)

    @Test
    fun `should be possible to start auction`() = runBlocking<Unit> {
        fakeAuctionRepository.save(AuctionFixtures.simpleAuction)

        handler.handleAsync(StartAuctionCommand(AuctionFixtures.simpleAuction._id))

        fakeAuctionRepository.publishedEvents shouldExist { it is AuctionStartedEvent }
        verify { fakeAuctionRepository.save(any()) }
        coVerify { fakeUnitOfWork.inTransactionAsync(any()) }
    }
}
