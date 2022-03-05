package dk.mailr.ordering.dataAccess

import com.mongodb.reactivestreams.client.ClientSession
import dk.mailr.buildingblocks.dataAccess.EntityRepository
import dk.mailr.buildingblocks.dataAccess.MongoEntityRepository
import dk.mailr.buildingblocks.mediator.EventPublisher
import dk.mailr.ordering.domain.Order
import org.litote.kmongo.coroutine.CoroutineDatabase

interface OrderRepository : EntityRepository<Order> {
    suspend fun getAll(): List<Order>
}

class MainOrderRepository(
    eventsPublisher: EventPublisher,
    mongoDatabase: CoroutineDatabase,
    clientSession: ClientSession,
) : OrderRepository, MongoEntityRepository<Order>(eventsPublisher, clientSession) {
    override val mongoCollection = mongoDatabase.getCollection<Order>()

    override suspend fun getAll(): List<Order> {
        return mongoCollection.find().toList()
    }
}
