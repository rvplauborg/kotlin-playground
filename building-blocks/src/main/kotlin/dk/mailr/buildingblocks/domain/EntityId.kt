package dk.mailr.buildingblocks.domain

import dk.mailr.buildingblocks.uuid.emptyUUID
import org.valiktor.functions.isNotEqualTo
import org.valiktor.validate
import java.util.UUID

@JvmInline
value class EntityId<T : DomainEntity<T>>(val value: UUID) {
    init {
        validate(this) {
            validate(EntityId<T>::value).isNotEqualTo(emptyUUID)
        }
    }

    override fun toString(): String {
        return value.toString()
    }
}
