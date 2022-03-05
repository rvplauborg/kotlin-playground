package dk.mailr.buildingblocks.fakes

import dk.mailr.buildingblocks.dataAccess.EntityRepository
import dk.mailr.buildingblocks.domain.DomainEntity
import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.buildingblocks.exceptions.NotFoundException
import dk.mailr.buildingblocks.mediator.DomainEvent

open class FakeRepository<TEntity : DomainEntity<TEntity>> : EntityRepository<TEntity> {
    val entities = mutableMapOf<EntityId<TEntity>, TEntity>()
    val publishedEvents = mutableListOf<DomainEvent>()

    override suspend fun save(entity: TEntity): TEntity {
        entities[entity._id] = entity
        return entity
    }

    override suspend fun delete(entity: TEntity) {
        entities.remove(entity._id, entity)
        publishedEvents.addAll(entity.domainEvents)
    }

    override suspend fun findById(id: EntityId<TEntity>): TEntity? {
        return entities[id]
    }

    override suspend fun getById(id: EntityId<TEntity>): TEntity {
        return entities[id] ?: throw NotFoundException("Entity with id $id does not exist")
    }

    override suspend fun notifyAll() {
        publishedEvents.addAll(entities.flatMap { it.value.domainEvents })
        entities.forEach { it.value.clearDomainEvents() }
    }

    override suspend fun findByIds(ids: List<EntityId<TEntity>>): List<TEntity> {
        return entities.values.filter { it._id in ids }
    }
}
