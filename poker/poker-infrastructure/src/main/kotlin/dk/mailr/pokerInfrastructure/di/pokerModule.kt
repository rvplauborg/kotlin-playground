package dk.mailr.pokerInfrastructure.di

import dk.mailr.pokerApplication.CreateGameCommandHandler
import dk.mailr.pokerApplication.GameRepository
import dk.mailr.pokerApplication.GetGameByIdQueryHandler
import dk.mailr.pokerInfrastructure.MainGameRepository
import org.koin.dsl.module

val pokerModule = module {
    single<GameRepository> { MainGameRepository(get(), get(), get()) }
    single { CreateGameCommandHandler(get(), get()) }
    single { GetGameByIdQueryHandler(get()) }
}
