package dk.mailr.ordering

import dk.mailr.ordering.features.addOrderRoute
import dk.mailr.ordering.features.deleteOrderRoute
import dk.mailr.ordering.features.getOrdersRoute
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.h1

fun Routing.orderingRouting() {
    route("ordering") {
        get {
            call.respondHtml {
                body {
                    h1 {
                        +"Ordering app"
                    }
                    a(href = "/ordering/orders") {
                        +"Get orders"
                    }
                    a(href = "/ordering/add-order") {
                        +"Add order"
                    }
                }
            }
        }
        getOrdersRoute()
        addOrderRoute()
        deleteOrderRoute()
    }
}
