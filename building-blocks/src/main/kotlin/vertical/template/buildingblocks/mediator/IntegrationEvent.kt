package vertical.template.buildingblocks.mediator

import com.trendyol.kediatr.Notification
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.UUID

abstract class IntegrationEvent : Notification {
    val id: UUID = UUID.randomUUID()
    val occurredOn: OffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC)
}
