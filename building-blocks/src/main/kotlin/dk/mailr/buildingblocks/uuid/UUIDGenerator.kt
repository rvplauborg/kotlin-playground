package dk.mailr.buildingblocks.uuid

import java.util.UUID

fun interface UUIDGenerator {
    fun generate(): UUID
}

internal class RandomUUIDGenerator : UUIDGenerator {
    override fun generate(): UUID = UUID.randomUUID()
}
