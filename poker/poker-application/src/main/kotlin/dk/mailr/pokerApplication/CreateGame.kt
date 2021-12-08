package dk.mailr.pokerApplication

import com.trendyol.kediatr.AsyncCommandHandler
import com.trendyol.kediatr.Command
import dk.mailr.buildingblocks.dataAccess.UnitOfWork
import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.buildingblocks.mediator.TransactionalCommandHandler
import dk.mailr.buildingblocks.uuid.UUIDGenerator
import dk.mailr.pokerDomain.TexasHoldEmGame
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import java.util.UUID

fun Route.createGameRoute(mediator: Mediator, uuidGenerator: UUIDGenerator) {
    post("/game/create-game") {
        val gameId = uuidGenerator.generate()
        mediator.executeCommandAsync(CreateGameCommand.of(gameId))
        call.respond(HttpStatusCode.OK, gameId)
    }
}

data class CreateGameCommand private constructor(internal val gameId: EntityId<TexasHoldEmGame>) : Command {
    companion object {
        fun of(gameId: UUID) = CreateGameCommand(EntityId(gameId))
    }
}

class CreateGameCommandHandler(private val gameRepository: GameRepository, unitOfWork: UnitOfWork) :
    TransactionalCommandHandler<CreateGameCommand>(unitOfWork), AsyncCommandHandler<CreateGameCommand> {
    override suspend fun handleInTransaction(command: CreateGameCommand) {
        gameRepository.save(TexasHoldEmGame.create(EntityId(UUID.randomUUID())))
        gameRepository.saveChanges()
    }
}