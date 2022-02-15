package dk.mailr.buildingblocks.dataAccess

import com.mongodb.client.ClientSession
import java.io.Closeable

interface UnitOfWork : Closeable {
    suspend fun inTransactionAsync(block: suspend () -> Unit)
}

open class MongoUnitOfWork(private val session: ClientSession) : UnitOfWork {
    override suspend fun inTransactionAsync(block: suspend () -> Unit) {
        @Suppress("TooGenericExceptionCaught") // we need to ensure that we abort in the general case
        try {
            session.startTransaction()
            block()
            session.commitTransaction()
        } catch (e: Exception) {
            session.abortTransaction()
            throw e
        }
    }

    override fun close() {
        session.close()
    }
}
