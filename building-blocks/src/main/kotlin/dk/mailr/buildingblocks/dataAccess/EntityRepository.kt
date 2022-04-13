package dk.mailr.buildingblocks.dataAccess

import com.mongodb.reactivestreams.client.ClientSession
import dk.mailr.buildingblocks.domain.DomainEntity
import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.buildingblocks.exceptions.NotFoundException
import dk.mailr.buildingblocks.mediator.EventPublisher
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.`in`

interface EntityRepository<TEntity : DomainEntity<TEntity>> {
    suspend fun findById(id: EntityId<TEntity>): TEntity?

    suspend fun findByIds(ids: List<EntityId<TEntity>>): List<TEntity>

    suspend fun getById(id: EntityId<TEntity>): TEntity

    suspend fun save(entity: TEntity): TEntity

    suspend fun delete(entity: TEntity)

    suspend fun notifyAll()
}

abstract class MongoEntityRepository<TEntity : DomainEntity<TEntity>>(
    private val eventsPublisher: EventPublisher,
    private val clientSession: ClientSession,
) : EntityRepository<TEntity> {
    abstract val mongoCollection: CoroutineCollection<TEntity>

    override suspend fun findById(id: EntityId<TEntity>): TEntity? {
        return mongoCollection.findOneById(id, clientSession)
    }

    override suspend fun getById(id: EntityId<TEntity>): TEntity {
        return mongoCollection.findOneById(id, clientSession) ?: throw NotFoundException("Entity $id does not exist")
    }

    override suspend fun findByIds(ids: List<EntityId<TEntity>>): List<TEntity> {
        return mongoCollection.find(clientSession, DomainEntity<TEntity>::_id `in` ids).toList()
    }

    override suspend fun save(entity: TEntity): TEntity {
        mongoCollection.save(clientSession, entity)
        eventsPublisher.enqueueEventsFrom(entity)
        return entity
    }

    override suspend fun delete(entity: TEntity) {
        mongoCollection.deleteOneById(clientSession, entity._id)
        eventsPublisher.enqueueEventsFrom(entity)
    }

    override suspend fun notifyAll() {
        eventsPublisher.publishAll()
    }
}
