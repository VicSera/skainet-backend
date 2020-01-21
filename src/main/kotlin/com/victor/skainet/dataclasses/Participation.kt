package com.victor.skainet.dataclasses

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "participation")
data class Participation(
        @EmbeddedId
        val participationKey: ParticipationKey = ParticipationKey(),

        @ManyToOne
        @MapsId("user_id")
        @JoinColumn(name = "user_id")
        val user: User = User(),

        @ManyToOne
        @MapsId("trip_id")
        @JoinColumn(name = "trip_id")
        val trip: Trip = Trip(),

        @Column
        var status: Status = Status.WAITING
        ) {

}