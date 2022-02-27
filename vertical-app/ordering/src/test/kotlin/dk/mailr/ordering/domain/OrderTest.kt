package dk.mailr.ordering.domain

import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.buildingblocks.fakes.fixture
import dk.mailr.ordering.domain.events.OrderCreatedEvent
import io.kotest.matchers.collections.shouldContain
import org.junit.jupiter.api.Test

internal class OrderTest {
    @Test
    fun `should contain proper domain event when creating order`() {
        val id = fixture<EntityId<Order>>()

        val order = Order.create(id, fixture())

        order.domainEvents shouldContain OrderCreatedEvent(id)
    }
}
