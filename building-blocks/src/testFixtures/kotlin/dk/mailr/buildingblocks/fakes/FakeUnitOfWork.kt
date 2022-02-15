package dk.mailr.buildingblocks.fakes

import dk.mailr.buildingblocks.dataAccess.UnitOfWork

class FakeUnitOfWork : UnitOfWork {
    override suspend fun inTransactionAsync(block: suspend () -> Unit) {
        block()
    }

    override fun close() {
        // do nothing
    }
}
