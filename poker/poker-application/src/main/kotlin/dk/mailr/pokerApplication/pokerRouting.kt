package dk.mailr.pokerApplication

import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.buildingblocks.uuid.UUIDGenerator
import io.ktor.routing.Routing

fun Routing.pokerRouting(mediator: Mediator, uuidGenerator: UUIDGenerator) {
    createGameRoute(mediator, uuidGenerator)
    getGameByIdRoute(mediator)
}
