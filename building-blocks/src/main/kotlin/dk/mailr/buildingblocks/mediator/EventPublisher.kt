package dk.mailr.buildingblocks.mediator

import dk.mailr.buildingblocks.domain.DomainEntity
import java.util.concurrent.LinkedBlockingQueue

interface EventPublisher {
    fun enqueueEventsFrom(entity: DomainEntity<*>)
    fun publishAll()
}

class DomainEventPublisher(
    private val mediator: Mediator,
) : EventPublisher {
    private val pendingEvents = LinkedBlockingQueue<DomainEvent>()

    override fun enqueueEventsFrom(entity: DomainEntity<*>) {
        val domainEvents = entity.domainEvents
        entity.clearDomainEvents()
        domainEvents.forEach {
            pendingEvents.add(it)
        }
    }

    override fun publishAll() {
        val eventsToPublish = pendingEvents.toList()
        pendingEvents.clear()
        eventsToPublish.forEach {
            mediator.publishNotification(it)
        }
    }
}
