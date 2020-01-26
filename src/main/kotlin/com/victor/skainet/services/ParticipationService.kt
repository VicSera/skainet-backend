package com.victor.skainet.services

import com.victor.skainet.dataclasses.Participation
import com.victor.skainet.dataclasses.ParticipationKey
import com.victor.skainet.dataclasses.Trip
import com.victor.skainet.dataclasses.User
import com.victor.skainet.repositories.ParticipationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ParticipationService(
    @Autowired val repository: ParticipationRepository
) {
    fun addParticipation(user: User, trip: Trip) {
        val participation = Participation(user, trip)

//        user.addParticipation(participation)
//        trip.addParticipation(participation)

        repository.save(participation)
    }

    fun getParticipation(userId: UUID, tripId: UUID) : Participation? {
        val key = ParticipationKey(userId, tripId)  // construct key to search for

        return repository.findById(key).orElse(null)
    }

    fun getForUser(userId: UUID) : Iterable<Participation> {
        return repository.findAllByUserId(userId)
    }

    fun getForTrip(tripId: UUID) : Iterable<Participation> {
        return repository.findAllByTripId(tripId)
    }

    fun acceptParticipation(participation: Participation) {
        participation.accept()

        repository.save(participation)
    }

    fun declineParticipation(participation: Participation) {
        participation.decline()

        repository.save(participation)
    }

    fun deleteParticipation(userId: UUID, tripId: UUID) : Boolean {
        val participation = repository.findById(ParticipationKey(userId, tripId)).orElse(null)
                ?: return false

        repository.delete(participation)
        return true
    }
}