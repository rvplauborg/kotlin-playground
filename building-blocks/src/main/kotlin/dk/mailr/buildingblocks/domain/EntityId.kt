package dk.mailr.buildingblocks.domain

import dk.mailr.buildingblocks.uuid.emptyUUID
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.valiktor.functions.isNotEqualTo
import org.valiktor.validate
import java.util.UUID

@Serializable
open class EntityId<T : DomainEntity<T>>(val value: @Contextual UUID) {
    init {
        validateId()
    }

    private fun validateId() {
        validate(this) {
            validate(EntityId<T>::value).isNotEqualTo(emptyUUID)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EntityId<*>

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value.toString()
    }
}
