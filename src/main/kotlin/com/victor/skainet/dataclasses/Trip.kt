package com.victor.skainet.dataclasses

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "trip")
data class Trip(
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
) {
        @Id
        @Column(length = 16)
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(
                name = "UUID",
                strategy = "org.hibernate.id.UUIDGenerator"
        )
        val id: UUID = UUID.randomUUID()

        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var driver: User? = User()

//        @OneToMany(
//                mappedBy = "trip"
//        )
//        val participationList: MutableList<Participation> = emptyList<Participation>().toMutableList()
//
//        fun addParticipation(participation: Participation) {
//                participationList.add(participation)
//        }

//        override fun equals(other: Any?): Boolean {
//                if (other is Trip)
//                        return other.id == this.id
//                return false
//        }
//
//        override fun hashCode(): Int {
//                return 31
//        }
}
