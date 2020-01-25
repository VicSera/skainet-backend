package com.victor.skainet.dataclasses

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "user")
data class User (
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

        @Id
        @Column(length = 16)
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(
                name = "UUID",
                strategy = "org.hibernate.id.UUIDGenerator"
        )
        override val id: UUID = UUID.randomUUID()

        @OneToMany(
                mappedBy = "user"
        )
        val participationList: MutableList<Participation> = emptyList<Participation>().toMutableList()
}