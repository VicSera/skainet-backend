package com.victor.skainet.repositories

import com.victor.skainet.dataclasses.Trip
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TripRepository : CrudRepository<Trip, UUID> {
    fun findAllByDriverId(driverId : UUID) : Iterable<Trip>

    @Query(value = "select trip from Trip trip where trip.driver.id <> :driverId")
    fun findAllExcludingDriverId(driverId : UUID) : Iterable<Trip>
}