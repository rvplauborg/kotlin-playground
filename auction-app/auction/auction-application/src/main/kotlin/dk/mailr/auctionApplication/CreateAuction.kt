package dk.mailr.auctionApplication

import com.trendyol.kediatr.AsyncCommandHandler
import com.trendyol.kediatr.Command
import dk.mailr.auctionDomain.Auction
import dk.mailr.buildingblocks.dataAccess.UnitOfWork
import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.buildingblocks.uuid.UUIDGenerator
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import java.util.UUID

fun Route.createAuctionRoute(mediator: Mediator, uuidGenerator: UUIDGenerator) {
    post("/auction/create-auction") {
        val auctionId = uuidGenerator.generate()
        mediator.executeCommandAsync(CreateAuctionCommand.of(auctionId))
        call.respond(HttpStatusCode.OK, CreateAuctionResponse(auctionId))
    }
}

class CreateAuctionCommand(internal val auctionId: EntityId<Auction>) : Command {
    companion object {
        fun of(auctionId: UUID): CreateAuctionCommand = CreateAuctionCommand(EntityId(auctionId))
    }
}

data class CreateAuctionResponse(val auctionId: UUID)

class CreateAuctionCommandAsyncHandler(
    private val auctionRepository: AuctionRepository,
    private val unitOfWork: UnitOfWork,
) : AsyncCommandHandler<CreateAuctionCommand> {
    override suspend fun handleAsync(command: CreateAuctionCommand) {
        unitOfWork.inTransactionAsync {
            auctionRepository.save(Auction.create(command.auctionId))
            auctionRepository.notifyAll()
        }
    }
}
