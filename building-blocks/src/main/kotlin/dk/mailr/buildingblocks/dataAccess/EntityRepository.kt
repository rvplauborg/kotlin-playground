package dk.mailr.buildingblocks.dataAccess

import com.mongodb.client.ClientSession
import com.mongodb.client.MongoCollection
import dk.mailr.buildingblocks.domain.DomainEntity
import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.buildingblocks.exceptions.NotFoundException
import dk.mailr.buildingblocks.mediator.EventPublisher
import org.litote.kmongo.deleteOneById
import org.litote.kmongo.findOneById
import org.litote.kmongo.`in`
import org.litote.kmongo.save

interface EntityRepository<TEntity : DomainEntity<TEntity>, TEntityId : EntityId<TEntity>> {
    fun findById(id: TEntityId): TEntity?

    fun findByIds(ids: List<TEntityId>): List<TEntity>

    fun getById(id: TEntityId): TEntity

    fun <S : TEntity> save(entity: S): S

    fun saveAll(entities: List<TEntity>): List<TEntity>

    fun delete(entity: TEntity)

    fun deleteById(id: TEntityId)

    fun saveChanges()
}

abstract class MongoEntityRepository<TEntity : DomainEntity<TEntity>, TEntityId : EntityId<TEntity>>(
    private val eventsPublisher: EventPublisher,
    private val clientSession: ClientSession,
) : EntityRepository<TEntity, TEntityId> {
    abstract val mongoCollection: MongoCollection<TEntity>

    override fun findById(id: TEntityId): TEntity? {
        return mongoCollection.findOneById(clientSession, id)
    }

    override fun getById(id: TEntityId): TEntity {
        return mongoCollection.findOneById(clientSession, id) ?: throw NotFoundException("Entity $id does not exist")
    }

    override fun findByIds(ids: List<TEntityId>): List<TEntity> {
        return mongoCollection.find(clientSession, DomainEntity<TEntity>::id `in` ids).toList()
    }

    override fun <S : TEntity> save(entity: S): S {
        mongoCollection.save(clientSession, entity)
        eventsPublisher.enqueueEventsFrom(entity)
        return entity
    }

    override fun saveAll(entities: List<TEntity>): List<TEntity> {
        entities.forEach { mongoCollection.save(clientSession, it) }
        return entities
    }

    override fun delete(entity: TEntity) {
        mongoCollection.deleteOneById(clientSession, entity.id)
        eventsPublisher.enqueueEventsFrom(entity)
    }

    override fun deleteById(id: TEntityId) {
        mongoCollection.deleteOneById(clientSession, id)
    }

    override fun saveChanges() {
        eventsPublisher.publishAll()
    }
}
