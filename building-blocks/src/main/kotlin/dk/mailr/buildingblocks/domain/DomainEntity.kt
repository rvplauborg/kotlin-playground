package dk.mailr.buildingblocks.domain

import dk.mailr.buildingblocks.mediator.DomainEvent

abstract class DomainEntity<T : DomainEntity<T, TId>, TId : EntityId> {
    abstract val id: TId

    private val _domainEvents = mutableListOf<DomainEvent>()

    fun clearDomainEvents() = _domainEvents.clear()

    fun addDomainEvent(domainEvent: DomainEvent) {
        _domainEvents.add(domainEvent)
    }

    val domainEvents get() = _domainEvents.toList()
}
