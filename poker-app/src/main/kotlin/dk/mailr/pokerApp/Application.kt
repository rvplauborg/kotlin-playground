package dk.mailr.pokerApp

import dk.mailr.pokerApp.game.createGameRoute
import dk.mailr.pokerApp.game.gameModule
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.routing.routing
import org.koin.ktor.ext.Koin

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(Koin) {
        modules(
            kediatrModule,
            gameModule,
        )
    }

    routing {
        createGameRoute()
    }
}
