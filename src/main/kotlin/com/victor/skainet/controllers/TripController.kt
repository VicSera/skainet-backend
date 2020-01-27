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

    @GetMapping(path = ["/users/{userId}/trips"])
    fun getAllTripsForUser(@PathVariable userId : UUID) : ResponseEntity<Iterable<Trip>> {
        userService.getUser(userId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        return ResponseEntity(tripService.findTripsDrivenBy(userId), HttpStatus.OK)
    }

    @GetMapping(path = ["/users/{username}/other-trips"])
    fun getAllTripsNotDrivenByUser(@PathVariable username : String) : ResponseEntity<Iterable<Trip>> {
        val userId = userService.getIdFromUsername(username)
                ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        return ResponseEntity(tripService.findTripsNotDrivenBy(userId), HttpStatus.OK)
    }

    @GetMapping(path = ["/trips/{tripId}"])
    fun getTripById(@PathVariable tripId: UUID) : ResponseEntity<Trip> {
        val trip = tripService.getTrip(tripId)
                ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        return ResponseEntity(trip, HttpStatus.OK)
    }

    @GetMapping(path = ["/trips/{tripId}/accepted"])
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
        tripService.getTrip(tripId)
                ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        return ResponseEntity(HttpStatus.OK)
    }

    @PutMapping(path = ["/trips/{tripId}"])
    fun updateTrip(@PathVariable tripId: UUID, @RequestBody trip : Trip) : ResponseEntity<Void> {
        if (tripId != trip.id)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        tripService.updateTrip(trip)
        return ResponseEntity(HttpStatus.OK)
    }

    @PostMapping(path = ["/trips"])
    fun addTrip(@RequestBody trip : Trip) : ResponseEntity<Trip> {
        tripService.addTrip(trip)

        return ResponseEntity(trip, HttpStatus.OK)
    }
}