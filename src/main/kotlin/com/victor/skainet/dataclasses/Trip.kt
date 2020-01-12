package com.victor.skainet.dataclasses

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Trip(
        @Id
        @Column(length = 16)
        override val id: UUID = UUID.randomUUID(),

        @Column(nullable = false)
        val driverId: UUID = UUID.randomUUID(),

        @Column(nullable = false)
        var date: Date = Date(),

        @Column(nullable = false)
        var maxPassengers: Int = 0,

        @Column(nullable = false)
        var location: String = "",

        @Column(nullable = true)
        var comment: String = "",

        @Column(nullable = false)
        var go: Boolean = true
): SkaiObject
