package dk.mailr.pokerApp.game

import io.ktor.application.Application
import io.ktor.routing.routing
import org.koin.dsl.module
import org.koin.ktor.ext.koin

fun Application.gameModule() {
    koin {
        modules(
            module {
                single { CreateGameCommandHandler() }
            }
        )
    }

    routing {
        createGameRoute()
    }
}
