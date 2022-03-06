package dk.mailr.ordering.features

import com.trendyol.kediatr.AsyncQueryHandler
import com.trendyol.kediatr.Query
import dk.mailr.buildingblocks.di.requestScope
import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.ordering.dataAccess.OrderRepository
import dk.mailr.ordering.domain.Order
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.routing.Route
import io.ktor.routing.get
import kotlinx.html.ButtonType
import kotlinx.html.FormMethod
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.p
import org.koin.core.component.getScopeId
import org.koin.java.KoinJavaComponent
import java.time.Instant

fun Route.getOrdersRoute() = get("/orders") {
    val scope = KoinJavaComponent.getKoin().createScope(context.request.getScopeId(), requestScope)
    val mediator by scope.inject<Mediator>()

    val orders = mediator.executeQueryAsync(GetOrdersQuery())
    call.respondHtml {
        body {
            h1 {
                +"Orders:"
            }
            orders.orders.map {
                p {
                    +"Order: ${it.createdAt}, ${it.name}"
                }
                form(action = "/ordering/delete-order/${it.id}", method = FormMethod.post) {
                    button(type = ButtonType.submit) {
                        +"Delete"
                    }
                }
            }
            a("/ordering") {
                +"Go back"
            }
            a("/ordering/add-order") {
                +"Add order"
            }
        }
    }
    scope.close()
}

class GetOrdersQueryResponseDTO(val orders: List<GetOrderDTO>)

class GetOrderDTO(val id: String, val name: String, val createdAt: Instant)

fun Order.toDTO() = GetOrderDTO(_id.value, name.value, createdAt)

class GetOrdersQuery : Query<GetOrdersQueryResponseDTO>

class GetOrdersQueryHandler(private val orderRepository: OrderRepository) : AsyncQueryHandler<GetOrdersQuery, GetOrdersQueryResponseDTO> {
    override suspend fun handleAsync(query: GetOrdersQuery): GetOrdersQueryResponseDTO {
        val orders = orderRepository.getAll()
        return GetOrdersQueryResponseDTO(orders.map { it.toDTO() })
    }
}
