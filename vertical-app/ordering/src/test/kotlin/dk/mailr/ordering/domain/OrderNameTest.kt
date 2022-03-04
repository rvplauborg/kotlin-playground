package dk.mailr.ordering.domain

import org.junit.jupiter.api.Test
import org.valiktor.test.shouldFailValidation

internal class OrderNameTest {
    @Test
    fun `should validate against empty name`() {
        shouldFailValidation<OrderName> { OrderName("") }
    }

    @Test
    fun `should validate against blank  name`() {
        shouldFailValidation<OrderName> { OrderName("   ") }
    }
}
