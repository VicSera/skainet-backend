package com.victor.skainet.controllers

import com.victor.skainet.dataclasses.Participation
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
class ParticipationController (
        @Autowired
        val participationService: ParticipationService,

        @Autowired
        val userService: UserService,

        @Autowired
        val tripService: TripService
) {
    @PostMapping(path = ["/add-participation/{userId}/{tripId}"])
    fun addParticipation(@PathVariable userId : UUID, @PathVariable tripId : UUID) : ResponseEntity<Void> {
        val user = userService.getUser(userId)
        val trip = tripService.getTrip(tripId)

        println(user)
        println(trip)

        if (user == null || trip == null)
            return ResponseEntity(HttpStatus.NOT_FOUND)

        participationService.addParticipation(user, trip)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping(path = ["/by-user/{userId}"])
    fun getUserParticipationList(@PathVariable userId: UUID) : Iterable<Participation> {
        return participationService.getForUser(userId)
    }

    @GetMapping(path = ["/by-trip/{tripId"])
    fun getTripParticipationList(@PathVariable tripId: UUID) : Iterable<Participation> {
        return participationService.getForTrip(tripId)
    }
}