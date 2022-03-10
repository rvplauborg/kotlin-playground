package dk.mailr.ordering.features

import com.trendyol.kediatr.AsyncQueryHandler
import com.trendyol.kediatr.Query
import dk.mailr.buildingblocks.di.requestScope
import dk.mailr.buildingblocks.domain.EntityId
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
import java.util.UUID

fun Route.getOrderRoute() = get("orders/{orderId}") {
    val orderId = call.parameters["orderId"] ?: throw IllegalArgumentException("No order id provided")
    val scope = getKoin().createScope(context.request.getScopeId(), requestScope)
    val mediator by scope.inject<Mediator>()
    val order = mediator.executeQueryAsync(GetOrderQuery.of(orderId))
    call.respond(HttpStatusCode.OK, order)
    scope.close()
}

data class GetOrderQueryResponseDTO(val order: GetOrderDTO)

class GetOrderQuery(internal val orderId: EntityId<Order>) : Query<GetOrderQueryResponseDTO> {
    companion object {
        fun of(id: String) = GetOrderQuery(EntityId(UUID.fromString(id)))
    }
}

class GetOrderQueryHandler(private val orderRepository: OrderRepository) : AsyncQueryHandler<GetOrderQuery, GetOrderQueryResponseDTO> {
    override suspend fun handleAsync(query: GetOrderQuery): GetOrderQueryResponseDTO {
        val order = orderRepository.getById(query.orderId)
        return GetOrderQueryResponseDTO(order.toDTO())
    }
}
