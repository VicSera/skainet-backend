package com.victor.skainet.repositories

import com.victor.skainet.dataclasses.Trip
import java.security.KeyException
import java.util.*

@org.springframework.stereotype.Repository
class InMemoryTripRepository {
    val contents : MutableMap<UUID, Trip> = mutableMapOf()

    fun add(newTrip: Trip) {
        contents[newTrip.id] = newTrip
    }

    fun delete(id: UUID) : Trip? {
        try {
            val removedTrip : Trip? = contents[id]
            contents.remove(id)
            return removedTrip
        }
        catch (exception: KeyException) {
            throw RepositoryException("The given id (${id}) does not exist.")
        }
    }

    fun get(id: UUID): Trip? {
        val obj : Trip? = contents[id]

        if (obj != null) {
            return obj
        }
        throw RepositoryException("The given id (${id}) does not exist.")
    }

    fun getAll() : List<Trip> {
        return contents.values.toList()
    }


}