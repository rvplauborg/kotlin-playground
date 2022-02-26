package dk.mailr.auctionDomain

import dk.mailr.auctionDomain.events.AuctionCreatedEvent
import dk.mailr.auctionDomain.events.AuctionStartedEvent
import dk.mailr.buildingblocks.domain.DomainEntity
import dk.mailr.buildingblocks.domain.EntityId
import org.valiktor.functions.isNotEqualTo
import org.valiktor.validate

data class Auction private constructor(
    override val _id: EntityId<Auction>,
    val name: AuctionName,
) : DomainEntity<Auction>() {
    var status = AuctionStatus.CREATED
        private set

    companion object {
        fun create(id: EntityId<Auction>, name: AuctionName): Auction {
            return Auction(id, name).also { it.addDomainEvent(AuctionCreatedEvent(id)) }
        }
    }

    fun start() {
        validate(this) {
            validate(Auction::status).isNotEqualTo(AuctionStatus.STARTED)
        }
        status = AuctionStatus.STARTED
        addDomainEvent(AuctionStartedEvent(_id))
    }
}
