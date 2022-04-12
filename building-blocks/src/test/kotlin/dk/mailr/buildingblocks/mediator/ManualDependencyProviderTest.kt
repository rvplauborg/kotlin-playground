package dk.mailr.buildingblocks.mediator

import dk.mailr.buildingblocks.fakes.fixture
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ManualDependencyProviderTest {
    @Test
    fun `should provide instance when given matching class`() {
        val testString = fixture<String>()
        val instance = ManualDependencyProvider(mapOf(String::class.java to lazy { testString }))
            .getSingleInstanceOf(String::class.java)

        instance shouldBe testString
    }

    private interface TestInterface
    private class TestImpl1 : TestInterface
    private class TestImpl2 : TestInterface
    private class TestImpl3 : TestInterface

    @Test
    fun `should provide relevant sub types for given interface`() {
        val subTypes = ManualDependencyProvider(
            mapOf(
                TestImpl1::class.java to lazy { TestImpl1() },
                TestImpl2::class.java to lazy { TestImpl2() },
                TestImpl3::class.java to lazy { TestImpl3() }
            )
        )
            .getSubTypesOf(TestInterface::class.java)

        subTypes shouldBe listOf(TestImpl1::class.java, TestImpl2::class.java, TestImpl3::class.java)
    }
}
