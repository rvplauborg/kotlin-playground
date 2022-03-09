package dk.mailr.ordering.features

import com.trendyol.kediatr.AsyncQueryHandler
import com.trendyol.kediatr.Query
import dk.mailr.buildingblocks.di.requestScope
import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.ordering.dataAccess.OrderRepository
import dk.mailr.ordering.domain.Order
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import org.koin.core.component.getScopeId
import org.koin.java.KoinJavaComponent.getKoin
import java.time.Instant

fun Route.getOrdersRoute() = get("/orders") {
    val scope = getKoin().createScope(context.request.getScopeId(), requestScope)
    val mediator by scope.inject<Mediator>()
    val orders = mediator.executeQueryAsync(GetOrdersQuery())
    call.respond(HttpStatusCode.OK, orders)
    scope.close()
}

data class GetOrdersQueryResponseDTO(val orders: List<GetOrderDTO>)

data class GetOrderDTO(val id: String, val name: String, val createdAt: Instant)

fun Order.toDTO() = GetOrderDTO(_id.value, name.value, createdAt)

class GetOrdersQuery : Query<GetOrdersQueryResponseDTO>

class GetOrdersQueryHandler(private val orderRepository: OrderRepository) : AsyncQueryHandler<GetOrdersQuery, GetOrdersQueryResponseDTO> {
    override suspend fun handleAsync(query: GetOrdersQuery): GetOrdersQueryResponseDTO {
        val orders = orderRepository.getAll()
        return GetOrdersQueryResponseDTO(orders.map { it.toDTO() })
    }
}
