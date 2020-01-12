package com.victor.skainet.services

import com.victor.skainet.dataclasses.Trip
import com.victor.skainet.repositories.InMemoryTripRepository
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

//        repository.add(Trip(UUID.randomUUID(), UUID.fromString("a0102fb1-413a-4131-8ff8-fbdb51034255"), Date(),
//                maxPassengers = 5, location = "Gara", comment = "No comment", go = true))
//        repository.add(Trip(UUID.randomUUID(), UUID.fromString("a0102fb1-413a-4131-8ff8-fbdb51034255"), Date(),
//                maxPassengers = 2, location = "Taietura", comment = "No comment", go = false))
//        repository.add(Trip(UUID.randomUUID(), UUID.fromString("a0102fb1-413a-4131-8ff8-fbdb51034255"), Date(),
//                maxPassengers = 1, location = "Floresti", comment = "No comment", go = true))

        println(repository.findAll())
    }

    fun findAll() : Iterable<Trip> {
        return repository.findAll()
    }

    fun findForUser(userId : UUID) : Iterable<Trip> {
        return repository.findAllByDriverId(userId)
    }

    fun addTrip(trip : Trip) {
        repository.save(trip)
    }

    fun deleteTrip(tripId : UUID) : Trip? {
        val deletedTrip = repository.findById(tripId)
        repository.deleteById(tripId)

        return deletedTrip.orElse(null)
    }

    fun getTrip(tripId : UUID) : Trip? {
        return repository.findById(tripId).orElse(null)
    }

    fun updateTrip(trip : Trip) : Trip {
        deleteTrip(trip.id)
        addTrip(trip)

        return trip
    }
}