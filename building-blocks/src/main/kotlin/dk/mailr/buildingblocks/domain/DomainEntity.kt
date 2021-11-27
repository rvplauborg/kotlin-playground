package dk.mailr.buildingblocks.domain

import dk.mailr.buildingblocks.mediator.DomainEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
abstract class DomainEntity<T : DomainEntity<T>> {
    @SerialName("_id")
    abstract val id: EntityId<T>

    private val _domainEvents = mutableListOf<DomainEvent>()

    fun clearDomainEvents() = _domainEvents.clear()

    fun addDomainEvent(domainEvent: DomainEvent) {
        _domainEvents.add(domainEvent)
    }

    val domainEvents get() = _domainEvents.toList()
}
