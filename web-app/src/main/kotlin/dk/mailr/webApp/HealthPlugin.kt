package dk.mailr.webApp

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.ApplicationFeature
import io.ktor.application.call
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.HttpStatusCode
import io.ktor.request.path
import io.ktor.response.respondText
import io.ktor.util.AttributeKey

data class Check(val checkName: String, val check: CheckFunction)
typealias CheckFunction = suspend () -> Boolean

class Health private constructor(private val config: HealthConfiguration) {
    fun interceptor(pipeline: ApplicationCallPipeline) {
        pipeline.intercept(ApplicationCallPipeline.Call) {
            val path = call.request.path()

            if (path == config.healthCheckPath) {
                val (status, responseBody) = processChecks(config.healthChecks)
                call.respondText(responseBody, Json, status)
                finish()
            }

            return@intercept
        }
    }

    private suspend fun processChecks(checkLinkedList: LinkedHashSet<Check>): Pair<HttpStatusCode, String> {
        val checksWithResults = checkLinkedList.associate { Pair(it.checkName, it.check()) }
        val status = if (checksWithResults.containsValue(false)) HttpStatusCode.InternalServerError else HttpStatusCode.OK
        return Pair(status, jacksonObjectMapper().writeValueAsString(checksWithResults))
    }

    companion object Feature : ApplicationFeature<ApplicationCallPipeline, HealthConfiguration, Health> {
        override val key = AttributeKey<Health>("Health")
        override fun install(pipeline: ApplicationCallPipeline, configure: HealthConfiguration.() -> Unit) =
            Health(HealthConfiguration().apply(configure)).apply { interceptor(pipeline) }
    }
}

class HealthConfiguration internal constructor() {
    internal var healthChecks = linkedSetOf<Check>()

    val healthCheckPath = "/health"

    fun healthChecks(init: CheckBuilder.() -> Unit) {
        healthChecks = CheckBuilder().apply(init).checks
    }
}

class CheckBuilder {
    val checks = linkedSetOf<Check>()

    fun check(name: String, check: CheckFunction) {
        checks.add(Check(name, check))
    }
}
