package dk.mailr.pokerApplication

import dk.mailr.buildingblocks.fakes.FakeUnitOfWork
import dk.mailr.buildingblocks.fakes.fixture
import dk.mailr.pokerDomain.events.GameCreatedEvent
import io.kotest.matchers.collections.shouldExist
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class CreateGameCommandHandlerTest {
    private val fakeGameRepository = spyk(FakeGameRepository())

    private val handler = CreateGameCommandHandler(fakeGameRepository, FakeUnitOfWork())

    @Test
    fun `should create game`() = runBlocking<Unit> {
        handler.handleAsync(CreateGameCommand.of(fixture()))

        verify { fakeGameRepository.save(any()) }
        fakeGameRepository.publishedEvents shouldExist { it is GameCreatedEvent }
    }
}
