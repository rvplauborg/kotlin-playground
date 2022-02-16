package dk.mailr.pokerApplication

import com.trendyol.kediatr.AsyncQueryHandler
import com.trendyol.kediatr.Query
import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.pokerDomain.TexasHoldEmGame
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import java.util.UUID

fun Route.getGameByIdRoute(mediator: Mediator) {
    get("/game/{gameId}") {
        val gameId = call.parameters["gameId"]?.let { UUID.fromString(it) } ?: throw IllegalArgumentException("Missing argument")
        mediator.executeQueryAsync(GetGameByIdQuery.of(gameId))
        call.respond(HttpStatusCode.OK, gameId)
    }
}

data class GetGameByIdQuery private constructor(internal val gameId: EntityId<TexasHoldEmGame>) : Query<GetGameByIdResponse> {
    companion object {
        fun of(gameId: UUID) = GetGameByIdQuery(EntityId(gameId))
    }
}

data class GetGameByIdResponse(val gameId: String)

class GetGameByIdQueryHandler(private val gameRepository: GameRepository) :
    AsyncQueryHandler<GetGameByIdQuery, GetGameByIdResponse> {
    override suspend fun handleAsync(query: GetGameByIdQuery): GetGameByIdResponse {
        val game = gameRepository.getById(query.gameId)
        return GetGameByIdResponse(game._id.value)
    }
}
