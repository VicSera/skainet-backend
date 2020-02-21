package com.victor.skainet.services

import com.victor.skainet.dataclasses.Status
import com.victor.skainet.dataclasses.Trip
import com.victor.skainet.repositories.ParticipationRepository
import com.victor.skainet.repositories.TripRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException

@Service
class TripService(
        @Autowired private val repository : TripRepository,
        @Autowired private val participationRepository: ParticipationRepository
) {
    fun findAll() : Iterable<Trip> {
        return repository.findAll()
    }

    fun findTripsNotDrivenBy(userId: UUID) : Iterable<Trip> {
        return repository.findAllExcludingDriverId(userId)
    }

    fun findTripsDrivenBy(userId: UUID) : Iterable<Trip> {
        return repository.findAllByDriverId(userId)
    }

    fun addTrip(trip: Trip) {
        trip.remainingSeats = trip.maxPassengers

        repository.save(trip)
    }

    fun deleteTrip(tripId: UUID) : Trip? {
        val deletedTrip = repository.findById(tripId)
        repository.deleteById(tripId)

        return deletedTrip.orElse(null)
    }

    fun getTrip(tripId: UUID) : Trip? {
        return repository.findById(tripId).orElse(null)
    }

    fun updateTrip(trip: Trip) {
        repository.save(trip)
    }

    fun refreshRemainingSeats(trip: Trip) {
        trip.remainingSeats = trip.maxPassengers - participationRepository.countTripParticipationsByStatus(
                tripId = trip.id, status = Status.ACCEPTED
        )
        repository.save(trip)
    }
}