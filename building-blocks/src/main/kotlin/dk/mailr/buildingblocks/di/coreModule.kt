package dk.mailr.buildingblocks.di

import com.mongodb.client.ClientSession
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import dk.mailr.buildingblocks.dataAccess.MongoUnitOfWork
import dk.mailr.buildingblocks.dataAccess.UnitOfWork
import dk.mailr.buildingblocks.json.UUIDSerializer
import dk.mailr.buildingblocks.uuid.RandomUUIDGenerator
import dk.mailr.buildingblocks.uuid.UUIDGenerator
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import org.koin.dsl.module
import org.litote.kmongo.KMongo

val coreModule = module {
    single { KMongo.createClient("mongodb://mongo1:30001/test") }
    single<ClientSession> { get<MongoClient>().startSession() }
    single<UnitOfWork> { MongoUnitOfWork(get()) }
    single<MongoDatabase> { get<MongoClient>().getDatabase("vertical-template-db") }
    single<UUIDGenerator> { RandomUUIDGenerator() }
    single {
        Json(Json.Default) {
            serializersModule = SerializersModule { contextual(UUIDSerializer) }
        }
    }
}
