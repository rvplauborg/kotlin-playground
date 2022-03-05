package dk.mailr.ordering

import dk.mailr.buildingblocks.fakes.FakeRepository
import dk.mailr.ordering.dataAccess.OrderRepository
import dk.mailr.ordering.domain.Order

class FakeOrderRepository : OrderRepository, FakeRepository<Order>() {
    override suspend fun getAll(): List<Order> {
        return entities.values.toList()
    }
}
