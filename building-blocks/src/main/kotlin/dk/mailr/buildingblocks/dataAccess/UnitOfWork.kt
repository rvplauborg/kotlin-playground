package dk.mailr.buildingblocks.dataAccess

import com.mongodb.reactivestreams.client.ClientSession
import org.litote.kmongo.coroutine.abortTransactionAndAwait
import org.litote.kmongo.coroutine.commitTransactionAndAwait

interface UnitOfWork {
    suspend fun inTransactionAsync(block: suspend () -> Unit)
}

open class MongoUnitOfWork(private val session: ClientSession) : UnitOfWork {
    override suspend fun inTransactionAsync(block: suspend () -> Unit) {
        @Suppress("TooGenericExceptionCaught") // we need to ensure that we abort in the general case
        try {
            session.startTransaction()
            block()
            session.commitTransactionAndAwait()
        } catch (e: Exception) {
            session.abortTransactionAndAwait()
            throw e
        }
    }
}
