package dk.mailr.webApp.ordering.features;

import dk.mailr.ordering.features.DeleteOrderCommand
import dk.mailr.webApp.shouldHaveStatus
import dk.mailr.webApp.withApiTestApplication
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import java.util.UUID
import kotlin.test.Test

class DeleteOrderApiTest {
    @Test
    fun `should be possible to delete an order`() {
        withApiTestApplication { mediator, _ ->
            val id = UUID.randomUUID()
            coEvery { mediator.executeCommandAsync(DeleteOrderCommand.of(id)) } just Runs

            val call = handleRequest(HttpMethod.Post, "/ordering/delete-order/$id")

            call.response shouldHaveStatus HttpStatusCode.OK
            coVerify { mediator.executeCommandAsync(DeleteOrderCommand.of(id)) }
        }
    }
}
