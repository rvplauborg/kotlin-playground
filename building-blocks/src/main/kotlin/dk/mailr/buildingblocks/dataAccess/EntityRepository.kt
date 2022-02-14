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

interface EntityRepository<TEntity : DomainEntity<TEntity>> {
    fun findById(id: EntityId<TEntity>): TEntity?

    fun findByIds(ids: List<EntityId<TEntity>>): List<TEntity>

    fun getById(id: EntityId<TEntity>): TEntity

    fun <S : TEntity> save(entity: S): S

    fun delete(entity: TEntity)

    suspend fun saveChanges()
}

abstract class MongoEntityRepository<TEntity : DomainEntity<TEntity>>(
    private val eventsPublisher: EventPublisher,
    private val clientSession: ClientSession,
) : EntityRepository<TEntity> {
    abstract val mongoCollection: MongoCollection<TEntity>

    override fun findById(id: EntityId<TEntity>): TEntity? {
        return mongoCollection.findOneById(clientSession, id)
    }

    override fun getById(id: EntityId<TEntity>): TEntity {
        return mongoCollection.findOneById(clientSession, id) ?: throw NotFoundException("Entity $id does not exist")
    }

    override fun findByIds(ids: List<EntityId<TEntity>>): List<TEntity> {
        return mongoCollection.find(clientSession, DomainEntity<TEntity>::id `in` ids).toList()
    }

    override fun <S : TEntity> save(entity: S): S {
        mongoCollection.save(clientSession, entity)
        eventsPublisher.enqueueEventsFrom(entity)
        return entity
    }

    override fun delete(entity: TEntity) {
        mongoCollection.deleteOneById(clientSession, entity.id)
        eventsPublisher.enqueueEventsFrom(entity)
    }

    override suspend fun saveChanges() {
        eventsPublisher.publishAll()
    }
}
