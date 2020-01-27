package com.victor.skainet.services

import com.victor.skainet.dataclasses.Trip
import com.victor.skainet.repositories.TripRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException

@Service
class TripService(
        @Autowired private val repository : TripRepository
) {
    init {
        println(repository.findAll())
    }

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
}