package dk.mailr.auctionInfrastructure

import com.mongodb.reactivestreams.client.ClientSession
import dk.mailr.auctionApplication.AuctionRepository
import dk.mailr.auctionDomain.Auction
import dk.mailr.buildingblocks.dataAccess.MongoEntityRepository
import dk.mailr.buildingblocks.mediator.EventPublisher
import org.litote.kmongo.coroutine.CoroutineDatabase

class MainAuctionRepository(
    eventsPublisher: EventPublisher,
    mongoDatabase: CoroutineDatabase,
    clientSession: ClientSession,
) : AuctionRepository, MongoEntityRepository<Auction>(eventsPublisher, clientSession) {
    override val mongoCollection = mongoDatabase.getCollection<Auction>()
}
