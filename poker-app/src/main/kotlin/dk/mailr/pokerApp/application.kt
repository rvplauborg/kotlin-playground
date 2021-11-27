package dk.mailr.pokerApp

import com.mongodb.client.ClientSession
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import dk.mailr.buildingblocks.dataAccess.MongoUnitOfWork
import dk.mailr.buildingblocks.dataAccess.UnitOfWork
import dk.mailr.pokerApp.game.gameModule
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import org.koin.core.logger.Level
import org.koin.dsl.module
import org.koin.ktor.ext.koin
import org.koin.logger.slf4jLogger
import org.litote.kmongo.KMongo

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(CallLogging)
    koin {
        slf4jLogger(Level.ERROR) // Must be ERROR until https://github.com/InsertKoinIO/koin/issues/1188 is fixed and Kotlin 1.6 is supported

        @Suppress("RemoveExplicitTypeArguments") // would rather like to be explicit here
        modules(
            kediatrModule,
            module {
                single<MongoClient> { KMongo.createClient() }
                single<ClientSession> { get<MongoClient>().startSession() }
                single<UnitOfWork> { MongoUnitOfWork(get<ClientSession>()) }
                single<MongoDatabase> { get<MongoClient>().getDatabase("vertical-template-db") }
            }
        )
    }

    gameModule()
}
