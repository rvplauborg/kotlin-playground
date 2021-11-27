package dk.mailr.buildingblocks.events

import dk.mailr.buildingblocks.fakes.FakeMediator
import dk.mailr.buildingblocks.fakes.TestEntity
import dk.mailr.buildingblocks.fakes.fixture
import dk.mailr.buildingblocks.mediator.DomainEvent
import dk.mailr.buildingblocks.mediator.DomainEventPublisher
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldHaveSize
import org.junit.jupiter.api.Test

internal class EntityDomainEventsPublisherTest {

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
    fun `publish should not publish anything when nothing is queued`() {
        entityEventsPublisher.publishAll()

        fakeCommandBus.notifications.shouldBeEmpty()
    }

    @Test
    fun `should publish queued events to bus on publish`() {
        val (entity, domainEvent) = entityWithEvent()
        entityEventsPublisher.enqueueEventsFrom(entity)

        entityEventsPublisher.publishAll()

        fakeCommandBus.notifications shouldContain domainEvent
    }

    @Test
    fun `should clear queued events on publish so they are not published again`() {
        val (entity, domainEvent) = entityWithEvent()
        entityEventsPublisher.enqueueEventsFrom(entity)

        entityEventsPublisher.publishAll()
        entityEventsPublisher.publishAll()

        fakeCommandBus.notifications shouldHaveSize 1 shouldContain domainEvent
    }

    private fun entityWithEvent(): Pair<TestEntity, DomainEvent> {
        val entity: TestEntity = fixture()
        val domainEvent = object : DomainEvent() {}
        entity.addDomainEvent(domainEvent)
        return Pair(entity, domainEvent)
    }
}
