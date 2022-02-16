package dk.mailr.buildingblocks.fakes

import dk.mailr.buildingblocks.domain.DomainEntity
import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.buildingblocks.mediator.DomainEvent

data class TestEntity(override val _id: EntityId<TestEntity>) : DomainEntity<TestEntity>() {
    var name: String = ""

    fun withName(name: String) = this.also { this.name = name }
    fun withEvent() = this.also { addDomainEvent(object : DomainEvent() {}) }
}
