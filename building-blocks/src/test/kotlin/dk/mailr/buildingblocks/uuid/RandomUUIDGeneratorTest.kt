package dk.mailr.buildingblocks.uuid

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class RandomUUIDGeneratorTest {
    @Test
    fun `should generate random UUID`() {
        val uuid = RandomUUIDGenerator().generate()

        uuid.version() shouldBe 4
    }
}
