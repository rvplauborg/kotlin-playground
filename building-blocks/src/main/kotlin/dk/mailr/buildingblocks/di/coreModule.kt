package dk.mailr.buildingblocks.di

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.ClientSession
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import dk.mailr.buildingblocks.dataAccess.MongoUnitOfWork
import dk.mailr.buildingblocks.dataAccess.UnitOfWork
import dk.mailr.buildingblocks.uuid.RandomUUIDGenerator
import dk.mailr.buildingblocks.uuid.UUIDGenerator
import org.bson.UuidRepresentation
import org.koin.dsl.module
import org.litote.kmongo.KMongo

val coreModule = module {
    single<MongoClient> {
        KMongo.createClient(
            MongoClientSettings.builder()
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(ConnectionString("mongodb://mongo1:30001"))
                .build()
        )
    }
    single<ClientSession> { get<MongoClient>().startSession() }
    single<UnitOfWork> { MongoUnitOfWork(get()) }
    single<MongoDatabase> { get<MongoClient>().getDatabase("vertical-template-db") }
    single<UUIDGenerator> { RandomUUIDGenerator() }
}
