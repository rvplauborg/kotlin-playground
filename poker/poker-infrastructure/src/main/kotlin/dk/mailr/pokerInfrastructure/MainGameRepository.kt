package dk.mailr.pokerInfrastructure

import com.mongodb.client.ClientSession
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import dk.mailr.buildingblocks.dataAccess.MongoEntityRepository
import dk.mailr.buildingblocks.mediator.EventPublisher
import dk.mailr.pokerApplication.GameRepository
import dk.mailr.pokerDomain.TexasHoldEmGame
import org.litote.kmongo.getCollection

class MainGameRepository(
    eventPublisher: EventPublisher,
    clientSession: ClientSession,
    mongoDatabase: MongoDatabase,
) : GameRepository, MongoEntityRepository<TexasHoldEmGame>(eventPublisher, clientSession) {
    override val mongoCollection: MongoCollection<TexasHoldEmGame> = mongoDatabase.getCollection()
}
