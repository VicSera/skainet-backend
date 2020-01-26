package com.victor.skainet.dataclasses

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "participation")
data class Participation(
        @ManyToOne
        @MapsId("user_id")
        @JoinColumn(name = "user_id")
        val user: User = User(),

        @ManyToOne
        @MapsId("trip_id")
        @JoinColumn(name = "trip_id")
        val trip: Trip = Trip()
        ) {

        @EmbeddedId
        val key: ParticipationKey = ParticipationKey(user.id, trip.id)

        @Column
        var status: Status = Status.WAITING
}