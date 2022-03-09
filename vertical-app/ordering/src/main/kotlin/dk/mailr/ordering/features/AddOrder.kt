package dk.mailr.ordering.features

import com.trendyol.kediatr.AsyncCommandHandler
import com.trendyol.kediatr.Command
import dk.mailr.buildingblocks.dataAccess.UnitOfWork
import dk.mailr.buildingblocks.di.requestScope
import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.ordering.dataAccess.OrderRepository
import dk.mailr.ordering.domain.Order
import dk.mailr.ordering.domain.OrderName
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.request.receive
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import kotlinx.html.ButtonType
import kotlinx.html.FormMethod
import kotlinx.html.InputType
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.input
import kotlinx.html.label
import org.koin.core.component.getScopeId
import org.koin.java.KoinJavaComponent
import java.util.UUID

data class AddOrderRequest(val orderName: String)

fun Route.addOrderRoute() {
    route("/add-order") {
        get {
            call.respondHtml {
                body {
                    h1 {
                        +"Add order"
                    }

                    form(action = "/ordering/add-order", method = FormMethod.post) {

                        input(type = InputType.text, name = "order-name") {
                            placeholder = "Order name"
                            label {
                                +"Order name"
                            }
                        }

                        button(type = ButtonType.submit) {
                            +"Add"
                        }
                    }
                }
            }
        }
        post {
            val request = call.receive<AddOrderRequest>()
            val scope = KoinJavaComponent.getKoin().createScope(context.request.getScopeId(), requestScope)
            val mediator by scope.inject<Mediator>()
            mediator.executeCommandAsync(AddOrderCommand.of(request.orderName))
            call.respondRedirect("/ordering/orders")
            scope.close()
        }
    }
}

data class AddOrderCommand private constructor(
    internal val orderName: OrderName,
) : Command {
    companion object {
        fun of(orderName: String) = AddOrderCommand(OrderName(orderName))
    }
}

class AddOrderCommandHandler(
    private val orderRepository: OrderRepository,
    private val unitOfWork: UnitOfWork,
) : AsyncCommandHandler<AddOrderCommand> {
    override suspend fun handleAsync(command: AddOrderCommand) {
        unitOfWork.inTransactionAsync {
            val order = Order.create(EntityId(UUID.randomUUID()), command.orderName)
            orderRepository.save(order)
            orderRepository.notifyAll()
        }
    }
}
