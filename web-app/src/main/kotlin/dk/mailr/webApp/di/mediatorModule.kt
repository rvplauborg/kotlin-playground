package dk.mailr.webApp.di

import com.trendyol.kediatr.CommandBusBuilder
import dk.mailr.auctionInfrastructure.AuctionHandlers
import dk.mailr.buildingblocks.di.sessionScope
import dk.mailr.buildingblocks.mediator.DomainEventPublisher
import dk.mailr.buildingblocks.mediator.EventPublisher
import dk.mailr.buildingblocks.mediator.MainMediator
import dk.mailr.buildingblocks.mediator.ManualDependencyProvider
import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.ordering.OrderHandlers
import org.koin.dsl.module

val mediatorModule = module {
    scope(sessionScope) {
        scoped {
            CommandBusBuilder(
                ManualDependencyProvider(get<AuctionHandlers>().map + get<OrderHandlers>().map)
            ).build()
        }
        scoped<Mediator> { MainMediator(get()) }
        scoped<EventPublisher> { DomainEventPublisher(get()) }
    }
}
