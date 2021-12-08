package dk.mailr.buildingblocks.mediator

import dk.mailr.buildingblocks.fakes.FakeMediator
import dk.mailr.buildingblocks.fakes.TestEntity
import dk.mailr.buildingblocks.fakes.fixture
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldHaveSingleItem
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
    suspend fun `publish should not publish anything when nothing is queued`() {
        entityEventsPublisher.publishAll()

        fakeCommandBus.notifications.shouldBeEmpty()
    }

    @Test
    suspend fun `should publish queued events to bus on publish`() {
        val (entity, domainEvent) = entityWithEvent()
        entityEventsPublisher.enqueueEventsFrom(entity)

        entityEventsPublisher.publishAll()

        fakeCommandBus.notifications.shouldContain(domainEvent)
    }

    @Test
    suspend fun `should clear queued events on publish so they are not published again`() {
        val (entity, domainEvent) = entityWithEvent()
        entityEventsPublisher.enqueueEventsFrom(entity)

        entityEventsPublisher.publishAll()
        entityEventsPublisher.publishAll()

        fakeCommandBus.notifications.shouldHaveSingleItem() shouldBeEqualTo domainEvent
    }

    private fun entityWithEvent(): Pair<TestEntity, DomainEvent> {
        val entity = fixture<TestEntity>()
        val domainEvent = object : DomainEvent() {}
        entity.addDomainEvent(domainEvent)
        return Pair(entity, domainEvent)
    }
}