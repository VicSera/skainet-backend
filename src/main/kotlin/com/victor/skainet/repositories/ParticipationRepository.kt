package com.victor.skainet.repositories

import com.victor.skainet.dataclasses.Participation
import com.victor.skainet.dataclasses.ParticipationKey
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
}