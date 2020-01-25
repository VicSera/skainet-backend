package com.victor.skainet.dataclasses

import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class ParticipationKey (
        @Column(name = "user_id", length = 16)
        val userId : UUID = UUID.randomUUID(),

        @Column(name = "trip_id", length = 16)
        val tripId : UUID = UUID.randomUUID()
) : Serializable {

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}