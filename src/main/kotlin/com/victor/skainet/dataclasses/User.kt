package com.victor.skainet.dataclasses

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
data class User (
        @Id
        @Column(length = 16)
        override val id: UUID = UUID.randomUUID(),

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
        ) : SkaiObject