package dk.mailr.buildingblocks.mediator

import dk.mailr.buildingblocks.fakes.FakeMediator
import dk.mailr.buildingblocks.fakes.TestEntity
import dk.mailr.buildingblocks.fakes.fixture
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSingleElement
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class DomainEventPublisherTest {
    private val fakeCommandBus = FakeMediator()
    private val entityEventsPublisher = DomainEventPublisher(fakeCommandBus)

    @Test
    fun `should clear the entity events on enqueue`() {
        val (entity, domainEvent) = entityWithEvent()
        entity.domainEvents shouldContain domainEvent

        entityEventsPublisher.enqueueEventsFrom(entity)

        entity.domainEvents.shouldBeEmpty()
    }

    @Test
    fun `bus should be empty before publish`() {
        fakeCommandBus.notifications.shouldBeEmpty()
    }

    @Test
    fun `publish should not publish anything when nothing is queued`() = runBlocking<Unit> {
        entityEventsPublisher.publishAll()

        fakeCommandBus.notifications.shouldBeEmpty()
    }

    @Test
    fun `should publish queued events to bus on publish`() = runBlocking<Unit> {
        val (entity, domainEvent) = entityWithEvent()
        entityEventsPublisher.enqueueEventsFrom(entity)

        entityEventsPublisher.publishAll()

        fakeCommandBus.notifications.shouldContain(domainEvent)
    }

    @Test
    fun `should clear queued events on publish so they are not published again`() = runBlocking<Unit> {
        val (entity, domainEvent) = entityWithEvent()
        entityEventsPublisher.enqueueEventsFrom(entity)

        entityEventsPublisher.publishAll()
        entityEventsPublisher.publishAll()

        fakeCommandBus.notifications shouldHaveSingleElement domainEvent
    }

    private fun entityWithEvent(): Pair<TestEntity, DomainEvent> {
        val entity = fixture<TestEntity>()
        val domainEvent = object : DomainEvent() {}
        entity.addDomainEvent(domainEvent)
        return Pair(entity, domainEvent)
    }
}
