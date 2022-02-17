package dk.mailr.auctionApplication

import dk.mailr.auctionDomain.Auction
import dk.mailr.buildingblocks.dataAccess.EntityRepository

interface AuctionRepository : EntityRepository<Auction>
