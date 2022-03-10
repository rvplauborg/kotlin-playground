package dk.mailr.auctionApplication

import com.trendyol.kediatr.AsyncQueryHandler
import com.trendyol.kediatr.Query
import dk.mailr.auctionDomain.Auction
import dk.mailr.buildingblocks.di.requestScope
import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.buildingblocks.mediator.Mediator
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import org.koin.core.component.getScopeId
import org.koin.java.KoinJavaComponent.getKoin
import java.util.UUID

fun Route.getAuctionRoute() {
    get("/get-auction/{id}") {
        val id = call.parameters["id"] ?: return@get call.respondText("Bad Request", status = HttpStatusCode.BadRequest)
        val scope = getKoin().createScope(context.request.getScopeId(), requestScope)
        val mediator by scope.inject<Mediator>()

        val response = mediator.executeQueryAsync(GetAuctionQuery.of(UUID.fromString(id)))
        scope.close()
        call.respond(HttpStatusCode.OK, response)
    }
}

data class GetAuctionQueryResponse(val auctionId: String, val auctionName: String)

class GetAuctionQuery private constructor(internal val auctionId: EntityId<Auction>) : Query<GetAuctionQueryResponse> {
    companion object {
        fun of(auctionId: UUID) = GetAuctionQuery(EntityId(auctionId))
    }
}

class GetAuctionQueryAsyncHandler(private val auctionRepository: AuctionRepository) : AsyncQueryHandler<GetAuctionQuery, GetAuctionQueryResponse> {
    override suspend fun handleAsync(query: GetAuctionQuery): GetAuctionQueryResponse {
        val auction = auctionRepository.getById(query.auctionId)
        return GetAuctionQueryResponse(auction._id.value, auction.name.value)
    }
}
