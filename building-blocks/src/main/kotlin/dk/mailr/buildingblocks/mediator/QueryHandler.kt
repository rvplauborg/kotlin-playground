package dk.mailr.buildingblocks.mediator

import com.trendyol.kediatr.QueryHandler

interface QueryHandler<TQuery : Query<TResponse>, TResponse> : QueryHandler<TQuery, TResponse>
