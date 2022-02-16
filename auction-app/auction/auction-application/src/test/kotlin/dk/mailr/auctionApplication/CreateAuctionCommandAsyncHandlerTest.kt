package dk.mailr.auctionApplication

import dk.mailr.auctionDomain.AuctionStatus
import dk.mailr.buildingblocks.fakes.FakeUnitOfWork
import dk.mailr.buildingblocks.fakes.fixture
import io.kotest.common.runBlocking
import io.kotest.matchers.collections.exist
import io.kotest.matchers.should
import org.junit.jupiter.api.Test

class CreateAuctionCommandAsyncHandlerTest {
    private val fakeAuctionRepository = FakeAuctionRepository()

    private val handler = CreateAuctionCommandAsyncHandler(fakeAuctionRepository, FakeUnitOfWork())

    @Test
    fun `should create auction when valid input`() = runBlocking<Unit> {
        val command = fixture<CreateAuctionCommand>()
        handler.handleAsync(command)

        fakeAuctionRepository.entities.values should exist { it.status == AuctionStatus.CREATED }
    }
}
