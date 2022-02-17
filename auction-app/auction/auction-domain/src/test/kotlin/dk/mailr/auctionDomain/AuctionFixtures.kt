package dk.mailr.auctionDomain

import dk.mailr.buildingblocks.fakes.fixture

object AuctionAny {
    val simpleAuction = Auction.create(fixture(), fixture())
    val startedAuction = Auction.create(fixture(), fixture()).also { it.start() }
}
