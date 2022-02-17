package dk.mailr.auctionDomain.events

import dk.mailr.auctionDomain.Auction
import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.buildingblocks.mediator.DomainEvent

data class AuctionStartedEvent(val auctionId: EntityId<Auction>) : DomainEvent()
