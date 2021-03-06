package com.victor.skainet.dataclasses

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
        @Enumerated(EnumType.STRING)
        var status: Status = Status.WAITING
                private set

        fun accept() {
                status = Status.ACCEPTED
        }

        fun decline() {
                status = Status.DECLINED
        }
}