package dk.mailr.ordering

import dk.mailr.buildingblocks.fakes.fixture
import dk.mailr.ordering.domain.Order
import dk.mailr.ordering.features.AddOrderCommand

object OrderFixtures {
    fun newOrder() = Order.create(fixture(), fixture())

    object Commands {
        fun addOrderCommand() = AddOrderCommand.of(fixture())
    }

    object Queries {

    }
}
