package dk.mailr.buildingblocks.dataAccess

import com.mongodb.client.ClientSession
import org.slf4j.LoggerFactory
import java.io.Closeable

interface UnitOfWork : Closeable {
    fun start()
    fun commit()
    fun abort()
}

open class MongoUnitOfWork(private val session: ClientSession) : UnitOfWork {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun start() {
        session.startTransaction()
    }

    override fun commit() {
        session.commitTransaction()
    }

    override fun abort() {
        session.abortTransaction()
    }

    override fun close() {
        stop()
    }

    private fun stop() {
        if (session.hasActiveTransaction()) {
            session.abortTransaction()
            logger.warn("ABORTED TRANSACTION on destroy. You must ensure commit has been called before exiting..")
        }
    }
}
