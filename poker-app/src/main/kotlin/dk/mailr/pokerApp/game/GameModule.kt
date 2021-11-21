package dk.mailr.pokerApp.game

import org.koin.dsl.module

val gameModule = module {
    single { CreateGameCommandHandler() }
}
