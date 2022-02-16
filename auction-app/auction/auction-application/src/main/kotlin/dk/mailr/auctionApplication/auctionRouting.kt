package dk.mailr.auctionApplication

import io.ktor.routing.Routing
import io.ktor.routing.route

fun Routing.auctionRouting() {
    route("auction") {
        createAuctionRoute()
        getAuctionRoute()
    }
}
