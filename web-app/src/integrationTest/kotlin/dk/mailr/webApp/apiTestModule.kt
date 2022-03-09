package dk.mailr.webApp

import io.ktor.application.Application

fun Application.apiTestModule() = module(TestContainerSetup.DB_CONTAINER.replicaSetUrl)
