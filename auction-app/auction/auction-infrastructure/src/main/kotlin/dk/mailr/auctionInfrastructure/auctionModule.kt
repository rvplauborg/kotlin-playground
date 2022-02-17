package dk.mailr.auctionInfrastructure

import dk.mailr.auctionApplication.AuctionRepository
import dk.mailr.auctionApplication.CreateAuctionCommandAsyncHandler
import dk.mailr.auctionApplication.GetAuctionQueryAsyncHandler
import dk.mailr.auctionApplication.StartAuctionCommandAsyncHandler
import dk.mailr.buildingblocks.di.sessionScope
import org.koin.core.scope.Scope
import org.koin.dsl.module

val auctionModule = module {
    scope(sessionScope) {
        scoped<AuctionRepository> { MainAuctionRepository(get(), get(), get()) }
        scoped { CreateAuctionCommandAsyncHandler(get(), get()) }
        scoped { GetAuctionQueryAsyncHandler(get()) }
        scoped { StartAuctionCommandAsyncHandler(get(), get()) }
        scoped {
            AuctionHandlers.of(
                classToLazyProvider<CreateAuctionCommandAsyncHandler>(),
                classToLazyProvider<GetAuctionQueryAsyncHandler>(),
                classToLazyProvider<StartAuctionCommandAsyncHandler>(),
            )
        }
    }
}

inline fun <reified T : Any> Scope.classToLazyProvider() = T::class.java to inject<T>()

class AuctionHandlers private constructor(val map: Map<Class<*>, Lazy<Any>>) {
    companion object {
        fun of(vararg pairs: Pair<Class<*>, Lazy<Any>>) = AuctionHandlers(mapOf(*pairs))
    }
}
