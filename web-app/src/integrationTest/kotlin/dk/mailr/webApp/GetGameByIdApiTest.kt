package dk.mailr.webApp

import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.pokerApplication.CreateGameCommand
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.koin.core.component.inject
import org.koin.test.KoinTest
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertNotNull

class GetGameByIdApiTest : KoinTest {
    @Test
    fun `should be possible to retrieve game by id`() {
        withTestApplication({ module() }) {
            val gameId = UUID.randomUUID()
            runBlocking {
                val mediator by inject<Mediator>()
                mediator.executeCommandAsync(CreateGameCommand.of(gameId))
            }

            handleRequest(HttpMethod.Get, "/game/$gameId").apply {
                response.status() shouldBeEqualTo HttpStatusCode.OK
                val actual = response.content
                assertNotNull(actual)
                response.content.shouldNotBeNull()
            }
        }
    }
}
