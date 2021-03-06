package dk.mailr.ordering.features

import com.trendyol.kediatr.AsyncCommandHandler
import com.trendyol.kediatr.Command
import dk.mailr.buildingblocks.dataAccess.UnitOfWork
import dk.mailr.buildingblocks.di.requestScope
import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.ordering.dataAccess.OrderRepository
import dk.mailr.ordering.domain.Order
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import org.koin.core.component.getScopeId
import org.koin.java.KoinJavaComponent.getKoin
import java.util.UUID

fun Route.deleteOrderRoute() = post("/delete-order/{id}") {
    val id = call.parameters["id"] ?: throw IllegalArgumentException("No id given")
    val scope = getKoin().createScope(context.request.getScopeId(), requestScope)
    val mediator by scope.inject<Mediator>()
    mediator.executeCommandAsync(DeleteOrderCommand.of(UUID.fromString(id)))
    call.respond(HttpStatusCode.OK, "Deleted")
    scope.close()
}

data class DeleteOrderCommand private constructor(val id: EntityId<Order>) : Command {
    companion object {
        fun of(id: UUID) = DeleteOrderCommand(EntityId(id))
    }
}

class DeleteOrderCommandHandler(
    private val orderRepository: OrderRepository,
    private val unitOfWork: UnitOfWork,
) : AsyncCommandHandler<DeleteOrderCommand> {
    override suspend fun handleAsync(command: DeleteOrderCommand) {
        unitOfWork.inTransactionAsync {
            val order = orderRepository.getById(command.id)
            order.delete()
            orderRepository.delete(order)
            orderRepository.notifyAll()
        }
    }
}
