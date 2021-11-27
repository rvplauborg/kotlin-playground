package dk.mailr.buildingblocks.dataaccess

import com.mongodb.client.ClientSession
import dk.mailr.buildingblocks.dataAccess.MongoUnitOfWork
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class MongoUnitOfWorkTest {
    private val mockClientSession = mockk<ClientSession>(relaxed = true)

    private val unitOfWork = MongoUnitOfWork(mockClientSession)

    @Test
    fun commit() {
        unitOfWork.commit()

        verify { mockClientSession.commitTransaction() }
    }

    @Test
    fun start() {
        unitOfWork.start()

        verify { mockClientSession.startTransaction() }
    }

    @Test
    fun abort() {
        unitOfWork.abort()

        verify { mockClientSession.abortTransaction() }
    }
}
