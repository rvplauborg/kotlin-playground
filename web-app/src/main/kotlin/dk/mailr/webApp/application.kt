package dk.mailr.webApp

import dk.mailr.buildingblocks.di.coreModule
import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.buildingblocks.uuid.UUIDGenerator
import dk.mailr.pokerApplication.pokerRouting
import dk.mailr.pokerInfrastructure.di.pokerModule
import dk.mailr.webApp.di.mediatorModule
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.locations.Locations
import io.ktor.routing.routing
import org.koin.core.logger.Level
import org.koin.ktor.ext.inject
import org.koin.ktor.ext.koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(dbConnectionString: String = environment.config.property("mongodb.uri").getString()) {
    install(CallLogging)
    koin {
        slf4jLogger(Level.ERROR) // Must be ERROR until https://github.com/InsertKoinIO/koin/issues/1188 is fixed
        modules(
            coreModule(dbConnectionString),
            mediatorModule,
            pokerModule,
        )
    }

    routing {
        val mediator by inject<Mediator>()
        val uuidGenerator by inject<UUIDGenerator>()
        pokerRouting(mediator, uuidGenerator)
    }

    val port = environment.config.propertyOrNull("ktor.deployment.port")?.getString()
    val env = environment.config.propertyOrNull("ktor.environment")?.getString()
    log.info("Running on port {} with environment {}", port, env)

    log.info("MongoDB URI $dbConnectionString")
}
