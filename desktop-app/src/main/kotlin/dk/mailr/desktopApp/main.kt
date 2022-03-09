package dk.mailr.desktopApp

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

fun main() = application {
    val httpClient = HttpClient(CIO) {
        install(Logging)
        install(HttpTimeout)
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

        expectSuccess = true
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose for Desktop",
        state = rememberWindowState(width = 600.dp, height = 600.dp)
    ) {
        MaterialTheme {
            FrontPage(httpClient)
        }
    }
}
