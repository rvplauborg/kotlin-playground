package dk.mailr.pokerApp

import com.trendyol.kediatr.CommandBusBuilder
import dk.mailr.buildingblocks.mediator.MainMediator
import dk.mailr.buildingblocks.mediator.ManuelDependencyProvider
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
