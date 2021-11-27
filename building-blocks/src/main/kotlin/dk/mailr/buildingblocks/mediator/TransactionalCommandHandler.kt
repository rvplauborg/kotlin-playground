package dk.mailr.buildingblocks.mediator

import com.trendyol.kediatr.Command
import com.trendyol.kediatr.CommandHandler
import dk.mailr.buildingblocks.dataAccess.UnitOfWork

abstract class TransactionalCommandHandler<TCommand : Command>(
    private val unitOfWork: UnitOfWork,
) : CommandHandler<TCommand> {
    final override fun handle(command: TCommand) {
        unitOfWork.use {
            it.start()
            handleInTransaction(command)
            it.commit()
        }
    }

    protected abstract fun handleInTransaction(command: TCommand)
}
