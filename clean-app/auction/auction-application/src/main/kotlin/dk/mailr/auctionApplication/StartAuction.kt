package dk.mailr.auctionApplication

import com.trendyol.kediatr.AsyncCommandHandler
import com.trendyol.kediatr.Command
import dk.mailr.auctionDomain.Auction
import dk.mailr.buildingblocks.dataAccess.UnitOfWork
import dk.mailr.buildingblocks.di.requestScope
import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.buildingblocks.mediator.Mediator
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import org.koin.core.component.getScopeId
import org.koin.java.KoinJavaComponent.getKoin
import java.util.UUID

fun Route.startAuctionRoute() {
    post("/start-auction") {
        val scope = getKoin().createScope(context.request.getScopeId(), requestScope)
        val mediator by scope.inject<Mediator>()

        val request = call.receive<StartAuctionRequest>()
        mediator.executeCommandAsync(StartAuctionCommand.of(request.auctionId))

        scope.close()
        call.respond(HttpStatusCode.OK)
    }
}

data class StartAuctionRequest(val auctionId: UUID)

class StartAuctionCommand(internal val auctionId: EntityId<Auction>) : Command {
    companion object {
        fun of(auctionId: UUID) = StartAuctionCommand(EntityId(auctionId))
    }
}

class StartAuctionCommandAsyncHandler(
    private val auctionRepository: AuctionRepository,
    private val unitOfWork: UnitOfWork,
) : AsyncCommandHandler<StartAuctionCommand> {
    override suspend fun handleAsync(command: StartAuctionCommand) {
        unitOfWork.inTransactionAsync {
            val auction = auctionRepository.getById(command.auctionId)
            auction.start()
            auctionRepository.save(auction)
            auctionRepository.notifyAll()
        }
    }
}
