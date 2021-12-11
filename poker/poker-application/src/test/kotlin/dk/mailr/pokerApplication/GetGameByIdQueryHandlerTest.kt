package dk.mailr.pokerApplication

import dk.mailr.buildingblocks.exceptions.NotFoundException
import dk.mailr.buildingblocks.fakes.fixture
import dk.mailr.pokerDomain.TexasHoldEmGame
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.coInvoking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldThrow
import org.junit.jupiter.api.Test

class GetGameByIdQueryHandlerTest {
    private val fakeGameRepository = FakeGameRepository()
    private val handler = GetGameByIdQueryHandler(fakeGameRepository)

    @Test
    fun `should be possible to get existing game`() = runBlocking<Unit> {
        val query = fixture<GetGameByIdQuery>()
        fakeGameRepository.entities[query.gameId] = TexasHoldEmGame.create(query.gameId)

        val result = handler.handleAsync(query)

        result.gameId shouldBeEqualTo query.gameId.value
    }

    @Test
    fun `should fail if game does not exist`() = runBlocking<Unit> {
        val query = fixture<GetGameByIdQuery>()

        coInvoking { handler.handleAsync(query) } shouldThrow NotFoundException::class
    }
}
