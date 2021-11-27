package dk.mailr.buildingblocks

import dk.mailr.buildingblocks.fakes.TestEntityId
import dk.mailr.buildingblocks.fakes.fixture
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.valiktor.test.shouldFailValidation
import java.util.UUID

internal class EntityIdTest {
    @Test
    fun `creation should fail with empty UUID`() {
        shouldFailValidation<TestEntityId> {
            TestEntityId(UUID(0, 0))
        }
    }

    @Test
    fun `creation should succeed with non-empty UUID`() {
        assertDoesNotThrow {
            TestEntityId(fixture())
        }
    }
}
