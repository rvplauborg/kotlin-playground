package vertical.template.buildingblocks.fakes

import com.trendyol.kediatr.Command
import com.trendyol.kediatr.CommandWithResult
import com.trendyol.kediatr.Notification
import com.trendyol.kediatr.Query
import vertical.template.buildingblocks.mediator.Mediator

class FakeMediator : Mediator {
    val commands = mutableListOf<Command>()
    val notifications = mutableListOf<Notification>()

    override fun <TCommand : Command> executeCommand(command: TCommand) {
        commands.add(command)
    }

    override fun <TCommand : CommandWithResult<TResult>, TResult> executeCommand(command: TCommand): TResult {
        throw NotImplementedError()
    }

    override suspend fun <TCommand : Command> executeCommandAsync(command: TCommand) {
        commands.add(command)
    }

    override suspend fun <TCommand : CommandWithResult<TResult>, TResult> executeCommandAsync(command: TCommand): TResult {
        throw NotImplementedError()
    }

    override fun <TQuery : Query<TResponse>, TResponse> executeQuery(query: TQuery): TResponse {
        throw NotImplementedError()
    }

    override suspend fun <TQuery : Query<TResponse>, TResponse> executeQueryAsync(query: TQuery): TResponse {
        throw NotImplementedError()
    }

    override fun <T : Notification> publishNotification(notification: T) {
        notifications.add(notification)
    }

    override suspend fun <T : Notification> publishNotificationAsync(notification: T) {
        notifications.add(notification)
    }
}
