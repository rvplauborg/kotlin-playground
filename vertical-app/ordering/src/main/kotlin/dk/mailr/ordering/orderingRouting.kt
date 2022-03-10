package dk.mailr.ordering

import dk.mailr.ordering.features.addOrderRoute
import dk.mailr.ordering.features.deleteOrderRoute
import dk.mailr.ordering.features.getOrderRoute
import dk.mailr.ordering.features.getOrdersRoute
import io.ktor.routing.Routing
import io.ktor.routing.route

fun Routing.orderingRouting() {
    route("ordering") {
        getOrdersRoute()
        getOrderRoute()
        addOrderRoute()
        deleteOrderRoute()
    }
}
