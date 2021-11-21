package dk.mailr.buildingblocks.mediator

import com.trendyol.kediatr.CommandHandler

interface CommandHandler<TCommand : Command> : CommandHandler<TCommand>
