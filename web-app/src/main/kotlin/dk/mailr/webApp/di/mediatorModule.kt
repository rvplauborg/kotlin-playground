package dk.mailr.webApp.di

import com.trendyol.kediatr.CommandBusBuilder
import dk.mailr.buildingblocks.mediator.DomainEventPublisher
import dk.mailr.buildingblocks.mediator.EventPublisher
import dk.mailr.buildingblocks.mediator.MainMediator
import dk.mailr.buildingblocks.mediator.ManualDependencyProvider
import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.pokerApplication.CreateGameCommandHandler
import dk.mailr.pokerApplication.GetGameByIdQueryHandler
import org.koin.dsl.module

val mediatorModule = module {
    single(createdAtStart = true) {
        CommandBusBuilder(
            // TODO split out Mediator DI configuration so each module configures its own handlers for injection
            ManualDependencyProvider(
                hashMapOf(
                    CreateGameCommandHandler::class.java to inject<CreateGameCommandHandler>(),
                    GetGameByIdQueryHandler::class.java to inject<GetGameByIdQueryHandler>(),
                )
            )
        ).build()
    }
    single<Mediator> { MainMediator(get()) }
    single<EventPublisher> { DomainEventPublisher(get()) }
}
