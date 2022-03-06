package dk.mailr.auctionInfrastructure

import dk.mailr.auctionApplication.AuctionRepository
import dk.mailr.auctionApplication.CreateAuctionCommandAsyncHandler
import dk.mailr.auctionApplication.GetAuctionQueryAsyncHandler
import dk.mailr.auctionApplication.StartAuctionCommandAsyncHandler
import dk.mailr.auctionApplication.auctionRouting
import dk.mailr.buildingblocks.di.classToLazyProvider
import dk.mailr.buildingblocks.di.coreModule
import dk.mailr.buildingblocks.di.requestScope
import io.ktor.application.Application
import io.ktor.routing.routing
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ktor.ext.koin

fun Application.auctionModule(dbConnectionString: String) {
    koin {
        modules(auctionKoinModule(dbConnectionString))
    }

    routing {
        auctionRouting()
    }
}

fun auctionKoinModule(dbConnectionString: String) = module {
    includes(coreModule(dbConnectionString))
    scope(requestScope) {
        scopedOf(::MainAuctionRepository) bind AuctionRepository::class
        scopedOf(::CreateAuctionCommandAsyncHandler)
        scopedOf(::GetAuctionQueryAsyncHandler)
        scopedOf(::StartAuctionCommandAsyncHandler)
        scoped {
            AuctionHandlers.of(
                classToLazyProvider<CreateAuctionCommandAsyncHandler>(),
                classToLazyProvider<GetAuctionQueryAsyncHandler>(),
                classToLazyProvider<StartAuctionCommandAsyncHandler>(),
            )
        }
    }
}

class AuctionHandlers private constructor(val map: Map<Class<*>, Lazy<Any>>) {
    companion object {
        fun of(vararg pairs: Pair<Class<*>, Lazy<Any>>) = AuctionHandlers(mapOf(*pairs))
    }
}
