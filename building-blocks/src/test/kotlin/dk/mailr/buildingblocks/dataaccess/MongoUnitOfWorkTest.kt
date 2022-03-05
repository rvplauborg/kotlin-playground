package dk.mailr.buildingblocks.dataaccess

import com.mongodb.reactivestreams.client.ClientSession
import dk.mailr.buildingblocks.dataAccess.MongoUnitOfWork
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.booleans.shouldBeTrue
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class MongoUnitOfWorkTest {
    private val mockClientSession = mockk<ClientSession>(relaxed = true)

    private val unitOfWork = MongoUnitOfWork(mockClientSession)

    @Test
    fun close() {
        unitOfWork.close()

        verify { mockClientSession.close() }
    }

    @Test
    fun handleInTransactionAsync() = runBlocking<Unit> {
        var bool = false

        unitOfWork.inTransactionAsync { bool = true }

        bool.shouldBeTrue()
        verifyOrder {
            mockClientSession.startTransaction()
            mockClientSession.commitTransaction()
        }
    }

    @Test
    fun `handleInTransactionAsync should abort on error`() = runBlocking<Unit> {
        shouldThrow<IllegalArgumentException> {
            unitOfWork.inTransactionAsync { throw IllegalArgumentException() }
        }

        verifyOrder {
            mockClientSession.startTransaction()
            mockClientSession.abortTransaction()
        }
    }
}
