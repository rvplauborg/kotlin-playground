package dk.mailr.buildingblocks

import org.junit.jupiter.api.BeforeEach
import org.litote.kmongo.KMongo
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
abstract class BaseEntityRepositoryTest {
    companion object {
        @Container
        val mongoDBContainer: MongoDBContainer = MongoDBContainer("mongo:5.0.4")
    }

    protected val mongoClient = KMongo.createClient("${mongoDBContainer.replicaSetUrl}?uuidRepresentation=STANDARD")
    protected val mongoDatabase = mongoClient.getDatabase("test")

    @BeforeEach
    fun clearDb() {
        mongoDatabase.drop()
    }
}
