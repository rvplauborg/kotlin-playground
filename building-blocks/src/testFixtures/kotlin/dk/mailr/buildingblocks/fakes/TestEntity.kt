package dk.mailr.buildingblocks.fakes

import dk.mailr.buildingblocks.domain.DomainEntity
import dk.mailr.buildingblocks.domain.EntityId
import java.util.UUID

class TestEntity(override val id: TestEntityId) : DomainEntity<TestEntity>()

class TestEntityId(value: UUID) : EntityId<TestEntity>(value)
