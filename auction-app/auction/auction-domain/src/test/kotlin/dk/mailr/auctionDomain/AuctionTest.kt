package dk.mailr.auctionDomain

import dk.mailr.auctionDomain.events.AuctionCreatedEvent
import dk.mailr.auctionDomain.events.AuctionStartedEvent
import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.buildingblocks.fakes.fixture
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.valiktor.test.shouldFailValidation

internal class AuctionTest {
    @Test
    fun `should have expected values when created`() {
        val id = fixture<EntityId<Auction>>()
        val name = fixture<AuctionName>()

        val auction = Auction.create(id, name)

        auction._id shouldBe id
        auction.name shouldBe name
        auction.status shouldBe AuctionStatus.CREATED
        auction.domainEvents shouldContain AuctionCreatedEvent(auction._id)
    }

    @Test
    fun `should be possible to start auction`() {
        val auction = AuctionAny.simpleAuction

        auction.start()

        auction.status shouldBe AuctionStatus.STARTED
        auction.domainEvents shouldContain AuctionStartedEvent(auction._id)
    }

    @Test
    fun `should not be possible to start already started auction`() {
        val auction = AuctionAny.startedAuction

        shouldFailValidation<Auction> {
            auction.start()
        }
    }
}
