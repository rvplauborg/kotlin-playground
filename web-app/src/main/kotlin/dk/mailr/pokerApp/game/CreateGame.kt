package dk.mailr.pokerApp.game

import com.trendyol.kediatr.AsyncCommandHandler
import dk.mailr.buildingblocks.mediator.Mediator
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import org.koin.ktor.ext.inject
import org.slf4j.LoggerFactory

fun Route.createGameRoute() {
    val mediator: Mediator by inject()
    post("/game/create-game") {
        mediator.executeCommandAsync(CreateGameCommand())
        call.respond(HttpStatusCode.OK)
    }
}

class CreateGameCommand : com.trendyol.kediatr.Command

class CreateGameCommandHandler : AsyncCommandHandler<CreateGameCommand> {
    private val logger = LoggerFactory.getLogger(javaClass)

    override suspend fun handleAsync(command: CreateGameCommand) {
        logger.info("Test")
    }
}
