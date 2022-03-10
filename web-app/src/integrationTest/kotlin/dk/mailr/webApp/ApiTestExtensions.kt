package dk.mailr.webApp

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.readValue
import dk.mailr.auctionInfrastructure.auctionModule
import dk.mailr.buildingblocks.json.jsonMapper
import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.buildingblocks.uuid.UUIDGenerator
import dk.mailr.ordering.orderingModule
import dk.mailr.webApp.di.mediatorModule
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.TestApplicationResponse
import io.ktor.server.testing.createTestEnvironment
import io.ktor.server.testing.withApplication
import io.mockk.mockk
import org.koin.ktor.ext.koin
import org.koin.logger.slf4jLogger
import kotlin.test.assertNotNull

fun Application.apiTestModule(
    mediatorOverride: Mediator,
    uuidGeneratorOverride: UUIDGenerator,
) {

    install(ContentNegotiation) {
        jackson {
            registerModule(JavaTimeModule())
        }
    }

    koin {
        slf4jLogger()
        modules(
            mediatorModule(mediatorOverride),
        )
    }
    auctionModule(uuidGenerator = uuidGeneratorOverride)
    orderingModule(uuidGenerator = uuidGeneratorOverride)
}

fun <R> withApiTestApplication(test: TestApplicationEngine.(mediator: Mediator, uuidGenerator: UUIDGenerator) -> R): R {
    return withApplication(createTestEnvironment()) {
        val mediator = mockk<Mediator>(relaxed = true)
        val uuidGenerator = mockk<UUIDGenerator>(relaxed = true)
        application.apiTestModule(mediator, uuidGenerator)
        test(mediator, uuidGenerator)
    }
}

inline infix fun <reified T : Any> TestApplicationResponse.shouldHaveContentEqualTo(response: T) {
    assertNotNull(content) {
        jsonMapper.readValue<T>(it) shouldBeEqualToComparingFields response
    }
}

infix fun TestApplicationResponse.shouldHaveStatus(status: HttpStatusCode) {
    this.status() shouldBe status
}
