package com.victor.skainet.dataclasses

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "user")
data class User (
        @Id
        @Column(length = 16)
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(
                name = "UUID",
                strategy = "org.hibernate.id.UUIDGenerator"
        )
        override val id: UUID = UUID.randomUUID(),

        @OneToMany(
                mappedBy = "driver",
                cascade = [CascadeType.ALL],
                orphanRemoval = true
        )
        val drivenTrips: MutableList<Trip> = emptyList<Trip>().toMutableList(),

//        @OneToMany(
//                mappedBy = "participation",
//                cascade = [CascadeType.ALL],
//                orphanRemoval = true
//        )
//        val trips : List<Trip> = emptyList(),

//        @ManyToMany(mappedBy = "participants")
//        val trips : List<Trip> = emptyList(),

        @Column(nullable = false)
        var firstName: String = "",

        @Column(nullable = false)
        var lastName: String = "",

        @Column(nullable = false, unique = true)
        var username: String = "",

        @Column(nullable = false)
        var password: String = "",

        @Column(nullable = true)
        var phoneNumber: String = "",

        @Column(nullable = true)
        var usualLocation: String = ""
        ) : SkaiObject {

        fun addDrivenTrip(trip: Trip) {
                drivenTrips.add(trip)
                trip.driver = this
        }

        fun removeDrivenTrip(trip: Trip) {
                drivenTrips.remove(trip)
                trip.driver = null
        }
}