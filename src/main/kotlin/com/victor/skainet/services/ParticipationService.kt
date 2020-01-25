package com.victor.skainet.services

import com.victor.skainet.dataclasses.Participation
import com.victor.skainet.dataclasses.ParticipationKey
import com.victor.skainet.dataclasses.Trip
import com.victor.skainet.dataclasses.User
import com.victor.skainet.repositories.ParticipationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ParticipationService(
    @Autowired val repository: ParticipationRepository
) {
    fun addParticipation(user: User, trip: Trip) {
        val participation = Participation(user, trip)

        user.addParticipation(participation)
        trip.addParticipation(participation)

        repository.save(participation)
    }
}