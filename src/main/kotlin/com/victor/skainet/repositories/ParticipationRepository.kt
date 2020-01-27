package com.victor.skainet.repositories

import com.victor.skainet.dataclasses.*
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ParticipationRepository : CrudRepository<Participation, ParticipationKey> {

    @Query(value = "select part from Participation part where part.key.userId = :userId ")
    fun findAllByUserId(userId : UUID) : List<Participation>

    @Query(value = "select part from Participation part where part.key.tripId = :tripId ")
    fun findAllByTripId(tripId : UUID) : List<Participation>

    @Query(value = "select part.user from Participation part where part.key.tripId = :tripId and part.status = 'ACCEPTED'")
    fun findAllParticipants(tripId: UUID): Iterable<User>

    @Query(value = "select part.user from Participation part where part.key.tripId = :tripId and part.status = 'WAITING'")
    fun findAllWaitingUsers(tripId: UUID) : Iterable<User>

    @Query(value = "select part.user from Participation part where part.key.tripId = :tripId and part.status = 'DECLINED'")
    fun findAllDeclinedUsers(tripId: UUID) : Iterable<User>

    @Query(value = "select part.trip from Participation part where part.key.userId = :userId and part.status = 'ACCEPTED'")
    fun findAllAcceptedTrips(userId: UUID) : Iterable<Trip>

    @Query(value = "select part.trip from Participation part where part.key.userId = :userId and part.status = 'WAITING'")
    fun findAllWaitingTrips(userId: UUID) : Iterable<Trip>

    @Query(value = "select part.trip from Participation part where part.key.userId = :userId and part.status = 'DECLINED'")
    fun findAllDeclinedTrips(userId: UUID) : Iterable<Trip>
}