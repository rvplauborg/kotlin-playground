package dk.mailr.webApp

import com.fasterxml.jackson.module.kotlin.readValue
import dk.mailr.buildingblocks.json.jsonMapper
import dk.mailr.buildingblocks.mediator.Mediator
import dk.mailr.buildingblocks.uuid.UUIDGenerator
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.TestApplicationResponse
import io.ktor.server.testing.createTestEnvironment
import io.ktor.server.testing.withApplication
import io.mockk.mockk
import kotlin.test.assertNotNull

fun <R> withApiTestApplication(test: TestApplicationEngine.(mediator: Mediator, uuidGenerator: UUIDGenerator) -> R): R {
    return withApplication(createTestEnvironment()) {
        val mediator = mockk<Mediator>(relaxed = true)
        val uuidGenerator = mockk<UUIDGenerator>(relaxed = true)
        application.module(TestContainerSetup.DB_CONTAINER.replicaSetUrl, mediator = mediator, uuidGenerator = uuidGenerator)
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
