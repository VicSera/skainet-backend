package com.victor.skainet.dataclasses

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "trip")
data class Trip(
        @Id
        @Column(length = 16)
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(
                name = "UUID",
                strategy = "org.hibernate.id.UUIDGenerator"
        )
        override val id: UUID = UUID.randomUUID(),

        @ManyToOne(fetch = FetchType.LAZY)
        var driver: User? = User(),

//        @OneToMany(
//                mappedBy = "participation",
//                cascade = [CascadeType.ALL],
//                orphanRemoval = true
//        )
//        val participants : List<User> = emptyList(),

//        @ManyToMany
//        @JoinTable(
//                name = "trip_user",
//                joinColumns = [JoinColumn(name = "trip_id")],
//                inverseJoinColumns = [JoinColumn(name = "user_id")]
//        )
//        val participants : List<User> = emptyList(),

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
): SkaiObject {

        override fun equals(other: Any?): Boolean {
                if (other is Trip)
                        return other.id == this.id
                return false
        }

        override fun hashCode(): Int {
                return 31
        }
}
