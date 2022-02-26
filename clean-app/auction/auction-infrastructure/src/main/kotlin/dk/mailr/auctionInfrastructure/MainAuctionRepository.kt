package dk.mailr.auctionInfrastructure

import com.mongodb.client.ClientSession
import com.mongodb.client.MongoDatabase
import dk.mailr.auctionApplication.AuctionRepository
import dk.mailr.auctionDomain.Auction
import dk.mailr.buildingblocks.dataAccess.MongoEntityRepository
import dk.mailr.buildingblocks.mediator.EventPublisher
import org.litote.kmongo.getCollection

class MainAuctionRepository(
    eventsPublisher: EventPublisher,
    mongoDatabase: MongoDatabase,
    clientSession: ClientSession,
) : AuctionRepository, MongoEntityRepository<Auction>(eventsPublisher, clientSession) {
    override val mongoCollection = mongoDatabase.getCollection<Auction>()
}
