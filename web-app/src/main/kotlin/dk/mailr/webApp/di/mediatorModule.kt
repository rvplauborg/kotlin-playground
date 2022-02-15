package dk.mailr.webApp.di

import com.trendyol.kediatr.CommandBusBuilder
import dk.mailr.auctionInfrastructure.AuctionHandlers
import dk.mailr.buildingblocks.mediator.DomainEventPublisher
import dk.mailr.buildingblocks.mediator.EventPublisher
import dk.mailr.buildingblocks.mediator.MainMediator
import dk.mailr.buildingblocks.mediator.ManualDependencyProvider
import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.pokerInfrastructure.di.PokerHandlers
import org.koin.dsl.module

val mediatorModule = module {
    single(createdAtStart = true) {
        CommandBusBuilder(
            ManualDependencyProvider(
                get<PokerHandlers>().map
                    .plus(get<AuctionHandlers>().map)
            )
        ).build()
    }
    single<Mediator> { MainMediator(get()) }
    single<EventPublisher> { DomainEventPublisher(get()) }
}
