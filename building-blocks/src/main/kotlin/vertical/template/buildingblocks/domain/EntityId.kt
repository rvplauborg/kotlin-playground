package vertical.template.buildingblocks.domain

import org.valiktor.functions.isNotEqualTo
import org.valiktor.validate
import vertical.template.buildingblocks.uuid.emptyUUID
import java.util.UUID

open class EntityId(val value: UUID) {
    init {
        validateId()
    }

    private fun validateId() {
        validate(this) {
            validate(EntityId::value).isNotEqualTo(emptyUUID)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EntityId

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
