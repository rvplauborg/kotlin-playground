package dk.mailr.auctionInfrastructure

import dk.mailr.auctionApplication.AuctionRepository
import dk.mailr.auctionApplication.CreateAuctionCommandAsyncHandler
import org.koin.dsl.module

val auctionModule = module {
    single<AuctionRepository> { MainAuctionRepository(get(), get(), get()) }
    single { CreateAuctionCommandAsyncHandler(get(), get()) }
    single {
        AuctionHandlers.of(
            CreateAuctionCommandAsyncHandler::class.java to inject<CreateAuctionCommandAsyncHandler>(),
        )
    }
}

class AuctionHandlers private constructor(val map: Map<Class<*>, Lazy<Any>>) {
    companion object {
        fun of(vararg pairs: Pair<Class<*>, Lazy<Any>>) = AuctionHandlers(mapOf(*pairs))
    }
}
