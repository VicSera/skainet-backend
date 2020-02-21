package com.victor.skainet.dataclasses

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "trip")
data class Trip (
        @Id
        @Column(length = 16)
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(
                name = "UUID",
                strategy = "org.hibernate.id.UUIDGenerator"
        )
        val id: UUID = UUID.randomUUID(),

        @ManyToOne(fetch = FetchType.LAZY)
        var driver: User? = User(),

        @Column(nullable = false)
        var dateTime: LocalDateTime = LocalDateTime.now(),

        @Column(nullable = false)
        var maxPassengers: Int = 0,

        @Column(nullable=false)
        var remainingSeats: Int = 0,

        @Column(nullable = false)
        var startingLocation: String = "",

        @Column(nullable = false)
        var destination: String = "",

        @Column(nullable = true)
        var comment: String = ""
)


