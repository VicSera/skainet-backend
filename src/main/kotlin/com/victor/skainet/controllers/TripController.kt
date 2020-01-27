package com.victor.skainet.controllers

import com.victor.skainet.dataclasses.Status
import com.victor.skainet.dataclasses.Trip
import com.victor.skainet.dataclasses.User
import com.victor.skainet.services.ParticipationService
import com.victor.skainet.services.TripService
import com.victor.skainet.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin(origins = ["http://localhost:4200"], allowCredentials = "true")
@RestController
class TripController (
        @Autowired val tripService: TripService,
        @Autowired val userService: UserService,
        @Autowired val participationService: ParticipationService
) {

    @GetMapping(path = ["/trips"])
    fun getAllTrips() : Iterable<Trip> {
        return tripService.findAll()
    }

    @GetMapping(path = ["/users/{username}/trips"])
    fun getAllTripsForUser(@PathVariable username : String) : Iterable<Trip>? {
        val userId : UUID? = userService.getIdFromUsername(username)

        if (userId != null)
            return tripService.findForUser(userId)

        return null
    }

    @GetMapping(path = ["/users/{username}/other-trips"])
    fun getAllTripsNotDrivenByUser(@PathVariable username : String) : Iterable<Trip>? {
        val userId : UUID? = userService.getIdFromUsername(username)

        if (userId != null)
            return tripService.findTripsNotDrivenBy(userId)

        return null
    }

    @GetMapping(path = ["/trips/{tripId}"])
    fun getTripById(@PathVariable tripId: UUID) : ResponseEntity<Trip> {
        val trip = tripService.getTrip(tripId)
        if (trip != null)
            return ResponseEntity(trip, HttpStatus.OK)
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @GetMapping(path = ["/trips/{tripId}/participants"])
    fun getTripParticipants(@PathVariable tripId: UUID) : Iterable<User> {
        return participationService.getUsersWithStatus(tripId, Status.ACCEPTED)
    }

    @GetMapping(path = ["/trips/{tripId}/declined"])
    fun getDeclinedUsers(@PathVariable tripId: UUID) : Iterable<User> {
        return participationService.getUsersWithStatus(tripId, Status.DECLINED)
    }

    @GetMapping(path = ["/trips/{tripId}/waiting"])
    fun getWaitingUsers(@PathVariable tripId: UUID) : Iterable<User> {
        return participationService.getUsersWithStatus(tripId, Status.WAITING)
    }

    @DeleteMapping(path = ["/trips/{tripId}"])
    fun deleteTripById(@PathVariable tripId: UUID) : ResponseEntity<Void> {
        val deletedTrip : Trip? = tripService.deleteTrip(tripId)

        if (deletedTrip != null) {
            return ResponseEntity(HttpStatus.OK)
//            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.notFound().build()
    }

    @PutMapping(path = ["/trips/{tripId}"])
    fun updateTrip(@PathVariable tripId: UUID, @RequestBody trip : Trip) : ResponseEntity<Trip> {
        tripService.updateTrip(trip)

        return ResponseEntity(trip, HttpStatus.OK)
    }

    @PostMapping(path = ["/trips"])
    fun addTrip(@RequestBody trip : Trip) : ResponseEntity<Trip> {
        tripService.addTrip(trip)

        return ResponseEntity(trip, HttpStatus.OK)
    }
}