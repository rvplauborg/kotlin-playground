package dk.mailr.buildingblocks.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import dk.mailr.buildingblocks.mediator.DomainEvent

abstract class DomainEntity<T : DomainEntity<T>> {
    abstract val id: EntityId<T>

    @Transient
    private val _domainEvents = mutableListOf<DomainEvent>()

    @get:JsonIgnore
    val domainEvents
        get() = _domainEvents.toList()

    fun clearDomainEvents() = _domainEvents.clear()

    fun addDomainEvent(domainEvent: DomainEvent) {
        _domainEvents.add(domainEvent)
    }
}
