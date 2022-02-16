package dk.mailr.buildingblocks.domain

import dk.mailr.buildingblocks.uuid.emptyUUID
import org.valiktor.functions.isNotEqualTo
import org.valiktor.validate
import java.util.UUID

data class EntityId<T : DomainEntity<T>> private constructor(val value: String) {
    companion object {
        operator fun <T : DomainEntity<T>> invoke(uuid: UUID): EntityId<T> = EntityId(uuid.toString())
    }

    init {
        validate(this) {
            validate(EntityId<T>::value).isNotEqualTo(emptyUUID.toString())
        }
    }
}
