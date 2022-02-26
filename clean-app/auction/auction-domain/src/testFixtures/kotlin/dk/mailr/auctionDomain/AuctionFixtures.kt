package dk.mailr.auctionDomain

import dk.mailr.buildingblocks.fakes.fixture

object AuctionFixtures {
    val simpleAuction = Auction.create(fixture(), fixture())
    val startedAuction = Auction.create(fixture(), fixture()).also { it.start() }
}
