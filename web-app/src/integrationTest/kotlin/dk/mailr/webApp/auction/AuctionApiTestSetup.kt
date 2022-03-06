package dk.mailr.webApp.auction

import dk.mailr.auctionApplication.CreateAuctionCommand
import dk.mailr.buildingblocks.di.requestScope
import dk.mailr.buildingblocks.fakes.fixture
import dk.mailr.buildingblocks.mediator.Mediator
import io.ktor.server.testing.TestApplicationEngine
import kotlinx.coroutines.runBlocking
import org.koin.java.KoinJavaComponent.getKoin
import java.util.UUID

fun TestApplicationEngine.createAuction(): UUID {
    val scope = getKoin().getOrCreateScope(fixture(), requestScope)
    val mediator: Mediator by scope.inject()
    val auctionId = UUID.randomUUID()
    runBlocking { mediator.executeCommandAsync(CreateAuctionCommand.of(auctionId, "auction")) }
    scope.close()
    return auctionId
}
