package com.example.ad.contract

import java.util.*

class ClientId(value: String) {
    val value: UUID

    init {
        this.value = this.runCatching { UUID.fromString(value) }
            .getOrElse { throw InvalidClientIdException() }
    }
}
