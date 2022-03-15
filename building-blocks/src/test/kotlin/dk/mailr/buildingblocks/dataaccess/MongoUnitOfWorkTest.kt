package dk.mailr.buildingblocks.dataaccess

import com.mongodb.reactivestreams.client.ClientSession
import dk.mailr.buildingblocks.dataAccess.MongoUnitOfWork
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.booleans.shouldBeTrue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.litote.kmongo.coroutine.abortTransactionAndAwait
import org.litote.kmongo.coroutine.commitTransactionAndAwait

internal class MongoUnitOfWorkTest {
    init {
        mockkStatic("org.litote.kmongo.coroutine.CoroutineClientSessionsKt") // ensuring extension functions are mocked
    }

    private val mockClientSession = mockk<ClientSession>(relaxed = true) {
        coEvery { this@mockk.commitTransactionAndAwait() } returns Unit
        coEvery { this@mockk.abortTransactionAndAwait() } returns Unit
    }

    private val unitOfWork = MongoUnitOfWork(mockClientSession)

    @Test
    fun handleInTransactionAsync() = runTest {
        var bool = false

        unitOfWork.inTransactionAsync { bool = true }

        bool.shouldBeTrue()
        coVerify {
            mockClientSession.startTransaction()
            mockClientSession.commitTransactionAndAwait()
        }
    }

    @Test
    fun `handleInTransactionAsync should abort on error`() = runTest {
        shouldThrow<IllegalArgumentException> {
            unitOfWork.inTransactionAsync { throw IllegalArgumentException() }
        }

        coVerifyOrder {
            mockClientSession.startTransaction()
            mockClientSession.abortTransactionAndAwait()
        }
    }
}
