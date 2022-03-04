package dk.mailr.ordering.domain

import dk.mailr.buildingblocks.domain.DomainEntity
import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.ordering.domain.events.OrderCreatedEvent
import dk.mailr.ordering.domain.events.OrderDeletedEvent

data class Order private constructor(override val _id: EntityId<Order>, val name: String) : DomainEntity<Order>() {
    fun delete() {
        addDomainEvent(OrderDeletedEvent(_id))
    }

    companion object {
        fun create(id: EntityId<Order>, name: String) = Order(id, name).also { it.addDomainEvent(OrderCreatedEvent(it._id)) }
    }
}
