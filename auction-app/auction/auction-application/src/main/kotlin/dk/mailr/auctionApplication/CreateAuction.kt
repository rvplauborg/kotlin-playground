package dk.mailr.auctionApplication

import com.trendyol.kediatr.AsyncCommandHandler
import com.trendyol.kediatr.Command
import dk.mailr.auctionDomain.Auction
import dk.mailr.auctionDomain.AuctionName
import dk.mailr.buildingblocks.dataAccess.UnitOfWork
import dk.mailr.buildingblocks.di.sessionScope
import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.buildingblocks.uuid.UUIDGenerator
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import org.koin.core.component.getScopeId
import org.koin.java.KoinJavaComponent.getKoin
import org.koin.ktor.ext.inject
import java.util.UUID

fun Route.createAuctionRoute() {
    val uuidGenerator by inject<UUIDGenerator>()
    post("/create-auction") {
        val scope = getKoin().createScope(context.request.getScopeId(), sessionScope)
        val mediator by scope.inject<Mediator>()

        val request = call.receive<CreateAuctionRequest>()
        val auctionId = uuidGenerator.generate()
        mediator.executeCommandAsync(CreateAuctionCommand.of(auctionId, request.auctionName))
        scope.close()
        call.respond(HttpStatusCode.OK, CreateAuctionResponse(auctionId))
    }
}

data class CreateAuctionRequest(val auctionName: String)
data class CreateAuctionResponse(val auctionId: UUID)

class CreateAuctionCommand(internal val auctionId: EntityId<Auction>, internal val auctionName: AuctionName) : Command {
    companion object {
        fun of(auctionId: UUID, auctionName: String) = CreateAuctionCommand(EntityId(auctionId), AuctionName(auctionName))
    }
}

class CreateAuctionCommandAsyncHandler(
    private val auctionRepository: AuctionRepository,
    private val unitOfWork: UnitOfWork,
) : AsyncCommandHandler<CreateAuctionCommand> {
    override suspend fun handleAsync(command: CreateAuctionCommand) {
        unitOfWork.inTransactionAsync {
            auctionRepository.save(Auction.create(command.auctionId, command.auctionName))
            auctionRepository.notifyAll()
        }
    }
}
