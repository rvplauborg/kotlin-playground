package vertical.template.buildingblocks.fakes

import vertical.template.buildingblocks.domain.DomainEntity
import vertical.template.buildingblocks.domain.EntityId
import java.util.UUID

class TestEntity(override val id: TestEntityId) : DomainEntity<TestEntity, TestEntityId>()

class TestEntityId(value: UUID) : EntityId(value)
