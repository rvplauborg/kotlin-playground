package dk.mailr.webApp.e2e

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import dk.mailr.webApp.module
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.testcontainers.containers.MongoDBContainer

@Suppress("UtilityClassWithPublicConstructor")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseE2ETest {
    companion object {
        private lateinit var server: NettyApplicationEngine
        private val DB_CONTAINER = MongoDBContainer("mongo:4.4.8")
        internal val httpClient = HttpClient(CIO) {
            install(JsonFeature) {
                serializer = JacksonSerializer {
                    registerModule(JavaTimeModule())
                }
                accept(ContentType.Application.Json)
            }
            defaultRequest {
                headers {
                    header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                }
            }
        }

        init {
            DB_CONTAINER.start()
        }

        @BeforeAll
        @JvmStatic
        fun setup() {
            val env = applicationEngineEnvironment {
                module {
                    module(DB_CONTAINER.replicaSetUrl)
                }
                // Public API
                connector {
                    host = "0.0.0.0"
                    port = 8081
                }
            }
            server = embeddedServer(Netty, env).start(false)
        }

        @AfterAll
        @JvmStatic
        fun teardown() {
            // clean up after this class, leave nothing dirty behind
            server.stop(1000, 10000)
            httpClient.close()
        }
    }
}
