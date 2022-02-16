package dk.mailr.auctionApplication

import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.buildingblocks.uuid.UUIDGenerator
import io.ktor.routing.Routing
import io.ktor.routing.route

fun Routing.auctionRouting(mediator: Mediator, uuidGenerator: UUIDGenerator) {
    route("auction") {
        createAuctionRoute(mediator, uuidGenerator)
        getAuctionRoute(mediator)
    }
}
