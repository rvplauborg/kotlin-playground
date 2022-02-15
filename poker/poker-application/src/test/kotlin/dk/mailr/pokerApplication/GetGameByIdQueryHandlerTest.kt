package dk.mailr.pokerApplication

import dk.mailr.buildingblocks.exceptions.NotFoundException
import dk.mailr.buildingblocks.fakes.fixture
import dk.mailr.pokerDomain.TexasHoldEmGame
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class GetGameByIdQueryHandlerTest {
    private val fakeGameRepository = FakeGameRepository()
    private val handler = GetGameByIdQueryHandler(fakeGameRepository)

    @Test
    fun `should be possible to get existing game`() = runBlocking<Unit> {
        val query = fixture<GetGameByIdQuery>()
        fakeGameRepository.entities[query.gameId] = TexasHoldEmGame.create(query.gameId)

        val result = handler.handleAsync(query)

        result.gameId shouldBe query.gameId.value
    }

    @Test
    fun `should fail if game does not exist`() = runBlocking<Unit> {
        val query = fixture<GetGameByIdQuery>()

        shouldThrow<NotFoundException> { handler.handleAsync(query) }
    }
}
