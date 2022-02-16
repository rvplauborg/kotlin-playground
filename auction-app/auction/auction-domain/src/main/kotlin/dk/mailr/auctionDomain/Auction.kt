package dk.mailr.auctionDomain

import dk.mailr.buildingblocks.domain.DomainEntity
import dk.mailr.buildingblocks.domain.EntityId

data class Auction private constructor(
    override val _id: EntityId<Auction>,
    val name: AuctionName,
) : DomainEntity<Auction>() {
    companion object {
        fun create(id: EntityId<Auction>, name: AuctionName): Auction {
            return Auction(id, name)
        }
    }
}
