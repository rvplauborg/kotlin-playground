package dk.mailr.webApp.di

import com.trendyol.kediatr.CommandBusBuilder
import dk.mailr.auctionInfrastructure.AuctionHandlers
import dk.mailr.buildingblocks.di.requestScope
import dk.mailr.buildingblocks.mediator.DomainEventPublisher
import dk.mailr.buildingblocks.mediator.EventPublisher
import dk.mailr.buildingblocks.mediator.MainMediator
import dk.mailr.buildingblocks.mediator.ManualDependencyProvider
import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.ordering.OrderHandlers
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.bind
import org.koin.dsl.module

// TODO: rplauborg 05/03/2022 If we could somehow modularize the mediator DI setup, so we could have API tests in each module,
//  so they only spin up their part of the application, that would be great.
fun mediatorModule(mediator: Mediator? = null) = module {
    scope(requestScope) {
        scoped {
            CommandBusBuilder(
                ManualDependencyProvider(get<AuctionHandlers>().map + get<OrderHandlers>().map)
            ).build()
        }
        scoped { mediator ?: MainMediator(get()) }
        scopedOf(::DomainEventPublisher) bind EventPublisher::class
    }
}
