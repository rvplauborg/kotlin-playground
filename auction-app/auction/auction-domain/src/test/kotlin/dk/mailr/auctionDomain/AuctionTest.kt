package dk.mailr.auctionDomain

import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.buildingblocks.fakes.fixture
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class AuctionTest {
    @Test
    fun `should have expected values when created`() {
        val id = fixture<EntityId<Auction>>()
        val name = fixture<AuctionName>()

        val auction = Auction.create(id, name)

        auction._id shouldBe id
        auction.name shouldBe name
        auction.status shouldBe AuctionStatus.CREATED
    }
}
