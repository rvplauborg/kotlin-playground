package dk.mailr.auctionApplication

import dk.mailr.auctionDomain.Auction
import dk.mailr.buildingblocks.fakes.FakeRepository

class FakeAuctionRepository : AuctionRepository, FakeRepository<Auction>()
