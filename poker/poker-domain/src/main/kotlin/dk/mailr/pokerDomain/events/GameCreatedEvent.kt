package dk.mailr.pokerDomain.events

import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.buildingblocks.mediator.DomainEvent
import dk.mailr.pokerDomain.TexasHoldEmGame

data class GameCreatedEvent(val gameId: EntityId<TexasHoldEmGame>) : DomainEvent()
