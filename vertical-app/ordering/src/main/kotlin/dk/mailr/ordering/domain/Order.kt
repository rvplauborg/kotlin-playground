package dk.mailr.ordering.domain

import dk.mailr.buildingblocks.domain.DomainEntity
import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.ordering.domain.events.OrderCreatedEvent

data class Order private constructor(override val _id: EntityId<Order>, val name: String) : DomainEntity<Order>() {
    companion object {
        fun create(id: EntityId<Order>, name: String) = Order(id, name).also { it.addDomainEvent(OrderCreatedEvent(it._id)) }
    }
}
