package dk.mailr.buildingblocks.mediator

import com.trendyol.kediatr.Notification
import java.time.Instant
import java.util.UUID

abstract class DomainEvent : Notification {
    val id: UUID = UUID.randomUUID()
    val occurredOn: Instant = Instant.now()
}
