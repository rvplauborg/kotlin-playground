package dk.mailr.buildingblocks.fakes

import dk.mailr.buildingblocks.dataAccess.UnitOfWork

class FakeUnitOfWork : UnitOfWork {
    override fun commit() {
        // do nothing
    }

    override fun start() {
        // do nothing
    }

    override fun abort() {
        // do nothing
    }

    override fun close() {
        // do nothing
    }
}
