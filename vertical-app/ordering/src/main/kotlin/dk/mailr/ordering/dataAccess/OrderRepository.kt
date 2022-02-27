package dk.mailr.ordering.dataAccess

import com.mongodb.client.ClientSession
import com.mongodb.client.MongoDatabase
import dk.mailr.buildingblocks.dataAccess.EntityRepository
import dk.mailr.buildingblocks.dataAccess.MongoEntityRepository
import dk.mailr.buildingblocks.mediator.EventPublisher
import dk.mailr.ordering.domain.Order
import org.litote.kmongo.find
import org.litote.kmongo.getCollection

interface OrderRepository : EntityRepository<Order> {
    fun getAll(): List<Order>
}

class MainOrderRepository(
    eventsPublisher: EventPublisher,
    mongoDatabase: MongoDatabase,
    clientSession: ClientSession,
) : OrderRepository, MongoEntityRepository<Order>(eventsPublisher, clientSession) {
    override val mongoCollection = mongoDatabase.getCollection<Order>()

    override fun getAll(): List<Order> {
        return mongoCollection.find<Order>().toList()
    }
}
