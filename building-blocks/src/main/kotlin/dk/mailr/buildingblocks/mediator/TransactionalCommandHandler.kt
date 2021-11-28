package dk.mailr.buildingblocks.mediator

import com.trendyol.kediatr.AsyncCommandHandler
import com.trendyol.kediatr.Command
import dk.mailr.buildingblocks.dataAccess.UnitOfWork

abstract class TransactionalCommandHandler<TCommand : Command>(
    private val unitOfWork: UnitOfWork,
) : AsyncCommandHandler<TCommand> {
    final override suspend fun handleAsync(command: TCommand) {
        unitOfWork.use {
            it.start()
            handleInTransaction(command)
            it.commit()
        }
    }

    protected abstract suspend fun handleInTransaction(command: TCommand)
}
