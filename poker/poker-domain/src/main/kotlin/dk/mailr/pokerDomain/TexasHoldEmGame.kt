package dk.mailr.pokerDomain

import dk.mailr.buildingblocks.domain.DomainEntity
import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.pokerDomain.events.GameCreatedEvent
import java.util.UUID

class TexasHoldEmGame private constructor(
    override val _id: EntityId<TexasHoldEmGame>,
) : DomainEntity<TexasHoldEmGame>() {
    companion object {
        fun create(id: EntityId<TexasHoldEmGame>) = TexasHoldEmGame(id).apply {
            addDomainEvent(GameCreatedEvent(id))
        }
    }
}
