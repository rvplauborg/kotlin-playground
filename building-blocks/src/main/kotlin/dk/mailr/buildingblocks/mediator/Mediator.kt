package dk.mailr.buildingblocks.mediator

import com.trendyol.kediatr.CommandBus

interface Mediator : CommandBus

class MainMediator(private val commandBus: CommandBus) : Mediator, CommandBus by commandBus
