package dk.mailr.webApp

import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.pokerApplication.CreateGameCommand
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlinx.coroutines.runBlocking
import org.koin.core.component.inject
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertNotNull

class GetGameByIdApiTest : BaseApiTest() {
    @Test
    fun `should be possible to retrieve game by id`() {
        withTestApplication({ module(DB_CONTAINER.replicaSetUrl) }) {
            val gameId = UUID.randomUUID()
            runBlocking {
                val mediator by inject<Mediator>()
                mediator.executeCommandAsync(CreateGameCommand.of(gameId))
            }

            handleRequest(HttpMethod.Get, "/game/$gameId").apply {
                response.status() shouldBe HttpStatusCode.OK
                response.content.shouldNotBeNull()
            }
        }
    }
}
