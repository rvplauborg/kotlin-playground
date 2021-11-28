package dk.mailr.pokerApp.di

import com.trendyol.kediatr.CommandBusBuilder
import dk.mailr.buildingblocks.mediator.DomainEventPublisher
import dk.mailr.buildingblocks.mediator.EventPublisher
import dk.mailr.buildingblocks.mediator.MainMediator
import dk.mailr.buildingblocks.mediator.ManualDependencyProvider
import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.pokerApplication.CreateGameCommandHandler
import org.koin.dsl.module

val mediatorModule = module {
    single(createdAtStart = true) {
        CommandBusBuilder(
            ManualDependencyProvider(
                hashMapOf(
                    CreateGameCommandHandler::class.java to inject<CreateGameCommandHandler>(),
                )
            )
        ).build()
    }
    single<Mediator> { MainMediator(get()) }
    single<EventPublisher> { DomainEventPublisher(get()) }
}
