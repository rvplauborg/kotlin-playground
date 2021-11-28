package dk.mailr.pokerApplication

import dk.mailr.buildingblocks.fakes.FakeUnitOfWork
import dk.mailr.buildingblocks.fakes.fixture
import dk.mailr.pokerDomain.events.GameCreatedEvent
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldContainAny
import org.junit.jupiter.api.Test

class CreateGameCommandHandlerTest {
    private val fakeGameRepository = spyk(FakeGameRepository())

    private val handler = CreateGameCommandHandler(fakeGameRepository, FakeUnitOfWork())

    @Test
    fun `should create game`() {
        runBlocking {
            handler.handleAsync(CreateGameCommand.of(fixture()))

            verify { fakeGameRepository.save(any()) }
            fakeGameRepository.publishedEvents.shouldContainAny { it is GameCreatedEvent }
        }
    }
}
