package dk.mailr.pokerApp.di

import dk.mailr.pokerApplication.CreateGameCommandHandler
import dk.mailr.pokerApplication.GameRepository
import dk.mailr.pokerInfrastructure.MainGameRepository
import org.koin.dsl.module

val gameModule = module {
    single<GameRepository> { MainGameRepository(get(), get(), get()) }
    single { CreateGameCommandHandler(get(), get()) }
}
