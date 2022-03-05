package dk.mailr.buildingblocks

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import kotlinx.coroutines.runBlocking
import org.bson.UuidRepresentation
import org.junit.jupiter.api.AfterEach
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
abstract class BaseEntityRepositoryTest {
    companion object {
        @Container
        val mongoDBContainer: MongoDBContainer = MongoDBContainer("mongo:4.4.8")
    }

    protected val mongoClient = KMongo.createClient(
        MongoClientSettings.builder()
            .uuidRepresentation(UuidRepresentation.STANDARD)
            .applyConnectionString(ConnectionString(mongoDBContainer.replicaSetUrl))
            .build()
    ).coroutine

    protected val mongoDatabase = mongoClient.getDatabase("test")

    @AfterEach
    fun clearDb() {
        runBlocking { mongoDatabase.drop() }
    }
}
