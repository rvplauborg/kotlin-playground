package dk.mailr.buildingblocks.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import dk.mailr.buildingblocks.mediator.DomainEvent

abstract class DomainEntity<T : DomainEntity<T>> {
    @get:JsonProperty("_id")
    abstract val id: EntityId<T>

    @JsonIgnore
    private val _domainEvents = mutableListOf<DomainEvent>()

    @get:JsonIgnore
    val domainEvents
        get() = _domainEvents.toList()

    fun clearDomainEvents() = _domainEvents.clear()

    fun addDomainEvent(domainEvent: DomainEvent) {
        _domainEvents.add(domainEvent)
    }
}
