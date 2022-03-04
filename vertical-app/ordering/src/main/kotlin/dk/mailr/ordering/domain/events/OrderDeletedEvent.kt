package dk.mailr.ordering.domain.events

import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.buildingblocks.mediator.DomainEvent
import dk.mailr.ordering.domain.Order

data class OrderDeletedEvent(val orderId: EntityId<Order>) : DomainEvent()
