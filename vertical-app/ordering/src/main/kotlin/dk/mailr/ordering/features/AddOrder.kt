package dk.mailr.ordering.features

import com.trendyol.kediatr.AsyncCommandHandler
import com.trendyol.kediatr.Command
import dk.mailr.buildingblocks.dataAccess.UnitOfWork
import dk.mailr.buildingblocks.di.requestScope
import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.buildingblocks.uuid.UUIDGenerator
import dk.mailr.ordering.dataAccess.OrderRepository
import dk.mailr.ordering.domain.Order
import dk.mailr.ordering.domain.OrderName
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import org.koin.core.component.getScopeId
import org.koin.java.KoinJavaComponent.getKoin
import org.koin.ktor.ext.inject
import java.util.UUID

data class AddOrderRequest(val orderName: String)

fun Route.addOrderRoute() = post("/add-order") {
    val request = call.receive<AddOrderRequest>()
    val scope = getKoin().createScope(context.request.getScopeId(), requestScope)
    val mediator by scope.inject<Mediator>()
    val uuidGenerator by call.inject<UUIDGenerator>()
    val id = uuidGenerator.generate()
    mediator.executeCommandAsync(AddOrderCommand.of(id, request.orderName))
    call.respond(id)
    scope.close()
}

data class AddOrderCommand private constructor(
    internal val orderId: EntityId<Order>,
    internal val orderName: OrderName,
) : Command {
    companion object {
        fun of(id: UUID, orderName: String) = AddOrderCommand(EntityId(id), OrderName(orderName))
    }
}

class AddOrderCommandHandler(
    private val orderRepository: OrderRepository,
    private val unitOfWork: UnitOfWork,
) : AsyncCommandHandler<AddOrderCommand> {
    override suspend fun handleAsync(command: AddOrderCommand) {
        unitOfWork.inTransactionAsync {
            val order = Order.create(command.orderId, command.orderName)
            orderRepository.save(order)
            orderRepository.notifyAll()
        }
    }
}
