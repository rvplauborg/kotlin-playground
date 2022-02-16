package dk.mailr.auctionInfrastructure

import dk.mailr.auctionApplication.AuctionRepository
import dk.mailr.auctionApplication.CreateAuctionCommandAsyncHandler
import dk.mailr.auctionApplication.GetAuctionQueryAsyncHandler
import org.koin.dsl.module

val auctionModule = module {
    single<AuctionRepository> { MainAuctionRepository(get(), get(), get()) }
    single { CreateAuctionCommandAsyncHandler(get(), get()) }
    single { GetAuctionQueryAsyncHandler(get()) }
    single {
        AuctionHandlers.of(
            CreateAuctionCommandAsyncHandler::class.java to inject<CreateAuctionCommandAsyncHandler>(),
            GetAuctionQueryAsyncHandler::class.java to inject<GetAuctionQueryAsyncHandler>(),
        )
    }
}

class AuctionHandlers private constructor(val map: Map<Class<*>, Lazy<Any>>) {
    companion object {
        fun of(vararg pairs: Pair<Class<*>, Lazy<Any>>) = AuctionHandlers(mapOf(*pairs))
    }
}
