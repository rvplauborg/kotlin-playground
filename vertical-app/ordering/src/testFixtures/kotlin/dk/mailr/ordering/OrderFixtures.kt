package dk.mailr.ordering

import dk.mailr.buildingblocks.fakes.fixture
import dk.mailr.ordering.domain.Order
import dk.mailr.ordering.features.AddOrderCommand
import dk.mailr.ordering.features.GetOrdersQuery
import java.util.UUID

object OrderFixtures {
    fun newOrder() = Order.create(fixture(), fixture())

    object Commands {
        val addOrderCommand = AddOrderCommand.of(UUID.randomUUID(), fixture())
    }

    object Queries {
        val getOrdersQuery = GetOrdersQuery()
    }
}
