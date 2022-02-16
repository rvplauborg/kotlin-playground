package dk.mailr.auctionApplication

import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.buildingblocks.uuid.UUIDGenerator
import io.ktor.routing.Routing

fun Routing.auctionRouting(mediator: Mediator, uuidGenerator: UUIDGenerator) {
    createAuctionRoute(mediator, uuidGenerator)
    getAuctionRoute(mediator)
}
