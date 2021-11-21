package dk.mailr.pokerApp

import com.trendyol.kediatr.CommandBusBuilder
import com.trendyol.kediatr.DependencyProvider
import dk.mailr.buildingblocks.mediator.MainMediator
import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.pokerApp.game.CreateGameCommandHandler
import org.koin.dsl.module

val kediatrModule = module(createdAtStart = true) {
    single {
        CommandBusBuilder(
            ManuelDependencyProvider(
                hashMapOf(
                    CreateGameCommandHandler::class.java to CreateGameCommandHandler(),
                )
            )
        ).build()
    }
    single<Mediator> { MainMediator(get()) }
}

@Suppress("UNCHECKED_CAST")
class ManuelDependencyProvider(
    private val handlerMap: HashMap<Class<*>, Any>,
) : DependencyProvider {
    override fun <T> getSingleInstanceOf(clazz: Class<T>): T {
        return handlerMap[clazz] as T
    }

    override fun <T> getSubTypesOf(clazz: Class<T>): Collection<Class<T>> {
        return handlerMap
            .filter { it.key.interfaces.contains(clazz) }
            .map { it.key as Class<T> }
    }
}
