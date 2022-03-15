package dk.mailr.webApp

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import dk.mailr.auctionInfrastructure.auctionModule
import dk.mailr.buildingblocks.di.coreModule
import dk.mailr.ordering.orderingModule
import dk.mailr.webApp.di.mediatorModule
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.CallId
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.features.callId
import io.ktor.features.callIdMdc
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.header
import io.ktor.request.httpMethod
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.routing.get
import io.ktor.routing.routing
import org.koin.ktor.ext.inject
import org.koin.ktor.ext.koin
import org.koin.logger.slf4jLogger
import org.litote.kmongo.coroutine.CoroutineClient
import java.util.concurrent.atomic.AtomicInteger

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(
    dbConnectionString: String = environment.config.property("mongodb.uri").getString(),
) {
    installCallId()
    installCallLogging()
    installStatusPages()
    installContentNegotiation()
    installHealthEndpoints()
    setupDependencyInjection(dbConnectionString)

    routing {
        get("/") {
            call.respondRedirect("/ordering/orders")
        }
    }

    auctionModule(dbConnectionString = dbConnectionString)
    orderingModule(dbConnectionString = dbConnectionString)

    val port = environment.config.propertyOrNull("ktor.deployment.port")?.getString()
    val env = environment.config.propertyOrNull("ktor.environment")?.getString()
    log.info("Running on port {} with environment {}", port, env)
    log.info("MongoDB URI $dbConnectionString")
}

private fun Application.installHealthEndpoints() {
    install(Health) {
        healthChecks {
            val db: CoroutineClient by inject()
            check("mongodb") {
                @Suppress("TooGenericExceptionCaught", "SwallowedException")
                try {
                    db.listDatabaseNames().toList()
                    true
                } catch (e: Exception) {
                    false
                }
            }
        }
    }
}

private fun Application.setupDependencyInjection(dbConnectionString: String) {
    koin {
        slf4jLogger()
        modules(
            coreModule(dbConnectionString = dbConnectionString),
            mediatorModule(),
        )
    }
}

private fun Application.installContentNegotiation() {
    install(ContentNegotiation) {
        jackson {
            registerModule(JavaTimeModule())
        }
    }
}

private fun Application.installStatusPages() {
    install(StatusPages) {
        exception<Throwable> {
            call.respond(HttpStatusCode.InternalServerError, "Something went wrong, excuse us!")
            throw it // otherwise, exception will be swallowed
            // A better solution besides of course handling more specific exceptions, is to use HTML pages,
            // see https://ktor.io/docs/status-pages.html#statusfile
        }
    }
}

private fun Application.installCallLogging() {
    install(CallLogging) {
        callIdMdc()
        format { call ->
            val status = call.response.status()
            val httpMethod = call.request.httpMethod.value
            val userAgent = call.request.headers["User-Agent"]
            "${call.request.uri}, Status: $status, HTTP method: $httpMethod, User agent: $userAgent, CallId: ${call.callId}"
        }
    }
}

private fun Application.installCallId() {
    install(CallId) {
        // Tries to retrieve a callId from an ApplicationCall.
        retrieve { it.request.header(HttpHeaders.XRequestId) }

        // If can't retrieve a callId from the ApplicationCall, it will try the generate blocks coalescing until one of them is not null.
        val counter = AtomicInteger(0)
        generate { "generated-call-id-${counter.getAndIncrement()}" }

        // Once a callId is generated, this optional function is called to verify if the retrieved or generated callId String is valid.
        verify { it.isNotEmpty() }
    }
}
