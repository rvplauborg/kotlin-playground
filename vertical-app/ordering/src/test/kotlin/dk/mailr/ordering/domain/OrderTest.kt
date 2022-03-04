package dk.mailr.ordering.domain

import dk.mailr.ordering.OrderFixtures
import dk.mailr.ordering.domain.events.OrderCreatedEvent
import dk.mailr.ordering.domain.events.OrderDeletedEvent
import io.kotest.matchers.collections.shouldContain
import org.junit.jupiter.api.Test

internal class OrderTest {
    @Test
    fun `should contain proper domain event when creating order`() {
        val order = OrderFixtures.newOrder()

        order.domainEvents shouldContain OrderCreatedEvent(order._id)
    }

    @Test
    fun `should contain proper domain event when deleting order`() {
        val order = OrderFixtures.newOrder()

        order.delete()

        order.domainEvents shouldContain OrderDeletedEvent(order._id)
    }
}
