package dk.mailr.buildingblocks.fakes

import dk.mailr.buildingblocks.dataAccess.EntityRepository
import dk.mailr.buildingblocks.domain.DomainEntity
import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.buildingblocks.exceptions.NotFoundException
import dk.mailr.buildingblocks.mediator.DomainEvent

open class FakeRepository<TEntity : DomainEntity<TEntity>> : EntityRepository<TEntity> {
    val entities = mutableMapOf<EntityId<TEntity>, TEntity>()
    val publishedEvents = mutableListOf<DomainEvent>()

    override fun <S : TEntity> save(entity: S): S {
        entities[entity.id] = entity
        return entity
    }

    override fun delete(entity: TEntity) {
        entities.remove(entity.id, entity)
    }

    override fun findById(id: EntityId<TEntity>): TEntity? {
        return entities[id]
    }

    override fun getById(id: EntityId<TEntity>): TEntity {
        return entities[id] ?: throw NotFoundException("Entity with id $id does not exist")
    }

    override suspend fun notifyAll() {
        publishedEvents.addAll(entities.flatMap { it.value.domainEvents })
        entities.forEach { it.value.clearDomainEvents() }
    }

    override fun findByIds(ids: List<EntityId<TEntity>>): List<TEntity> {
        return entities.values.filter { it.id in ids }
    }
}
