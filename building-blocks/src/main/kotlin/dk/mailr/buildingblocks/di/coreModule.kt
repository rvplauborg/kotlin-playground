package dk.mailr.buildingblocks.di

import com.mongodb.ConnectionString
import dk.mailr.buildingblocks.dataAccess.MongoUnitOfWork
import dk.mailr.buildingblocks.dataAccess.UnitOfWork
import dk.mailr.buildingblocks.uuid.RandomUUIDGenerator
import dk.mailr.buildingblocks.uuid.UUIDGenerator
import kotlinx.coroutines.runBlocking
import org.koin.dsl.module
import org.koin.dsl.onClose
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

fun coreModule(dbConnectionString: String) = module {
    single { KMongo.createClient(ConnectionString(dbConnectionString)).coroutine }
    single { get<CoroutineClient>().getDatabase("vertical-template-db") }
    scope(sessionScope) {
        scoped { runBlocking { get<CoroutineClient>().startSession() } } onClose {
            it?.close()
        }
        scoped<UnitOfWork> { MongoUnitOfWork(get()) }
    }
    single<UUIDGenerator> { RandomUUIDGenerator() }
}
