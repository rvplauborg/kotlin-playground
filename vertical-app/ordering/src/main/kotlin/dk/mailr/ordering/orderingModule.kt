package dk.mailr.ordering

import dk.mailr.buildingblocks.di.classToLazyProvider
import dk.mailr.buildingblocks.di.sessionScope
import dk.mailr.ordering.dataAccess.MainOrderRepository
import dk.mailr.ordering.dataAccess.OrderRepository
import dk.mailr.ordering.features.AddOrderCommandHandler
import dk.mailr.ordering.features.DeleteOrderCommandHandler
import dk.mailr.ordering.features.GetOrdersQueryHandler
import io.ktor.application.Application
import io.ktor.routing.routing
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ktor.ext.koin

fun Application.orderingModule() {
    koin {
        modules(orderingModule)
    }

    routing {
        orderingRouting()
    }
}

val orderingModule = module {
    scope(sessionScope) {
        scopedOf(::MainOrderRepository) bind OrderRepository::class
        scopedOf(::GetOrdersQueryHandler)
        scopedOf(::AddOrderCommandHandler)
        scopedOf(::DeleteOrderCommandHandler)
        scoped {
            OrderHandlers.of(
                classToLazyProvider<GetOrdersQueryHandler>(),
                classToLazyProvider<AddOrderCommandHandler>(),
                classToLazyProvider<DeleteOrderCommandHandler>(),
            )
        }
    }
}

class OrderHandlers private constructor(val map: Map<Class<*>, Lazy<Any>>) {
    companion object {
        fun of(vararg pairs: Pair<Class<*>, Lazy<Any>>) = OrderHandlers(mapOf(*pairs))
    }
}
