package dk.mailr.pokerApp

import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.buildingblocks.uuid.UUIDGenerator
import dk.mailr.buildingblocks.di.coreModule
import dk.mailr.pokerInfrastructure.di.pokerModule
import dk.mailr.pokerApp.di.mediatorModule
import dk.mailr.pokerApplication.createGameRoute
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.routing.routing
import io.ktor.serialization.json
import org.koin.core.logger.Level
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject
import org.koin.ktor.ext.koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(CallLogging)
    koin {
        slf4jLogger(Level.ERROR) // Must be ERROR until https://github.com/InsertKoinIO/koin/issues/1188 is fixed
        modules(
            coreModule,
            pokerModule,
            mediatorModule,
        )
    }
    install(ContentNegotiation) { json(get()) }

    routing {
        val mediator by inject<Mediator>()
        val uuidGenerator by inject<UUIDGenerator>()
        createGameRoute(mediator, uuidGenerator)
    }
}
