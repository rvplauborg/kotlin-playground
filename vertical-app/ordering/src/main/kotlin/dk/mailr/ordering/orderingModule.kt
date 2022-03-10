package dk.mailr.ordering

import dk.mailr.buildingblocks.di.classToLazyProvider
import dk.mailr.buildingblocks.di.coreModule
import dk.mailr.buildingblocks.di.requestScope
import dk.mailr.ordering.dataAccess.MainOrderRepository
import dk.mailr.ordering.dataAccess.OrderRepository
import dk.mailr.ordering.features.AddOrderCommandHandler
import dk.mailr.ordering.features.DeleteOrderCommandHandler
import dk.mailr.ordering.features.GetOrderQueryHandler
import dk.mailr.ordering.features.GetOrdersQueryHandler
import io.ktor.application.Application
import io.ktor.routing.routing
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ktor.ext.koin

fun Application.orderingModule(dbConnectionString: String) {
    koin {
        modules(orderingKoinModule(dbConnectionString))
    }

    routing {
        orderingRouting()
    }
}

fun orderingKoinModule(dbConnectionString: String) = module {
    includes(coreModule(dbConnectionString))
    scope(requestScope) {
        scopedOf(::MainOrderRepository) bind OrderRepository::class
        scopedOf(::GetOrdersQueryHandler)
        scopedOf(::AddOrderCommandHandler)
        scopedOf(::DeleteOrderCommandHandler)
        scopedOf(::GetOrderQueryHandler)
        scoped {
            OrderHandlers.of(
                classToLazyProvider<GetOrdersQueryHandler>(),
                classToLazyProvider<AddOrderCommandHandler>(),
                classToLazyProvider<DeleteOrderCommandHandler>(),
                classToLazyProvider<GetOrderQueryHandler>(),
            )
        }
    }
}

class OrderHandlers private constructor(val map: Map<Class<*>, Lazy<Any>>) {
    companion object {
        fun of(vararg pairs: Pair<Class<*>, Lazy<Any>>) = OrderHandlers(mapOf(*pairs))
    }
}
