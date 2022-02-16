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
import org.litote.kmongo.KMongo

fun coreModule(dbConnectionString: String) = module {
    // TODO scoping, currently everything is singleton
    single<MongoClient> { KMongo.createClient(ConnectionString(dbConnectionString)) }
    single<ClientSession> { get<MongoClient>().startSession() }
    single<UnitOfWork> { MongoUnitOfWork(get()) }
    single<MongoDatabase> { get<MongoClient>().getDatabase("vertical-template-db") }
    single<UUIDGenerator> { RandomUUIDGenerator() }
}
