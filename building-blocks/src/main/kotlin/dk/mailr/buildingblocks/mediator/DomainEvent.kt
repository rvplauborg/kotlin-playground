package dk.mailr.buildingblocks.mediator

import com.trendyol.kediatr.Notification
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.UUID

@Serializable
abstract class DomainEvent : Notification {
    val id: @Contextual UUID = UUID.randomUUID()
    val occurredOn: @Contextual OffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC)
}
