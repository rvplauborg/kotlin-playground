package dk.mailr.buildingblocks.di

import com.mongodb.ConnectionString
import com.mongodb.client.ClientSession
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import dk.mailr.buildingblocks.dataAccess.MongoUnitOfWork
import dk.mailr.buildingblocks.dataAccess.UnitOfWork
import dk.mailr.buildingblocks.uuid.RandomUUIDGenerator
import dk.mailr.buildingblocks.uuid.UUIDGenerator
import org.koin.dsl.module
import org.koin.dsl.onClose
import org.litote.kmongo.KMongo

fun coreModule(dbConnectionString: String) = module {
    single { KMongo.createClient(ConnectionString(dbConnectionString)) }
    single<MongoDatabase> { get<MongoClient>().getDatabase("vertical-template-db") }
    scope(sessionScope) {
        scoped<ClientSession> { get<MongoClient>().startSession() }
        scoped<UnitOfWork> { MongoUnitOfWork(get()) } onClose {
            it?.close()
        }
    }
    single<UUIDGenerator> { RandomUUIDGenerator() }
}
