package dk.mailr.auctionDomain

import dk.mailr.buildingblocks.domain.DomainEntity
import dk.mailr.buildingblocks.domain.EntityId

data class Auction private constructor(
    override val id: EntityId<Auction>,
) : DomainEntity<Auction>() {
    companion object {
        fun create(id: EntityId<Auction>): Auction {
            return Auction(id)
        }
    }
}
