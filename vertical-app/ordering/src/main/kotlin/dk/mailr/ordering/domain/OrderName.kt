package dk.mailr.ordering.domain

import org.valiktor.functions.isNotBlank
import org.valiktor.validate

@JvmInline
value class OrderName(val value: String) {
    init {
        validate(this) {
            validate(OrderName::value).isNotBlank()
        }
    }
}
