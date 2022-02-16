package dk.mailr.buildingblocks.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import dk.mailr.buildingblocks.mediator.DomainEvent

abstract class DomainEntity<T : DomainEntity<T>> {
    @Suppress("PropertyName", "VariableNaming") // For now with current KMongo setup we need to name it _id explicitly
    abstract val _id: EntityId<T>

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
