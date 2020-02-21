package com.victor.skainet.controllers

import com.victor.skainet.ParticipationException
import com.victor.skainet.dataclasses.ParticipationKey
import com.victor.skainet.dataclasses.Status
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
        @Autowired val participationService: ParticipationService,
        @Autowired val userService: UserService,
        @Autowired val tripService: TripService
) {
    @PostMapping(path = ["/participations"])
    fun addParticipation(@RequestBody participationKey: ParticipationKey) : ResponseEntity<Void> {
        val user = userService.getUser(participationKey.userId)
        val trip = tripService.getTrip(participationKey.tripId)

        if (user == null || trip == null)
            return ResponseEntity(HttpStatus.NOT_FOUND)

        if (participationService.getParticipation(participationKey.userId, participationKey.tripId) != null)
            return ResponseEntity(HttpStatus.CONFLICT)

        try {
            participationService.addParticipation(user, trip)
        } catch (excp: ParticipationException) {
            return ResponseEntity(HttpStatus.NOT_ACCEPTABLE)
        }

        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping(path = ["/participations/{userId}/{tripId}"])
    fun getParticipationStatus(@PathVariable userId: UUID, @PathVariable tripId: UUID) : ResponseEntity<Status> {
        val participation = participationService.getParticipation(userId, tripId)
                ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        return ResponseEntity(participation.status, HttpStatus.OK)
    }

    @PutMapping(path = ["/participations/{userId}/{tripId}/accept"])
    fun acceptParticipation(@PathVariable userId: UUID, @PathVariable tripId: UUID) : ResponseEntity<Void> {
        val participation = participationService.getParticipation(userId, tripId)
                ?: return ResponseEntity(HttpStatus.NOT_FOUND)  // if participation not found, return 404

        participationService.acceptParticipation(participation)
        tripService.refreshRemainingSeats(participation.trip)
        return ResponseEntity(HttpStatus.OK)
    }

    @PutMapping(path = ["/participations/{userId}/{tripId}/decline"])
    fun declineParticipation(@PathVariable userId: UUID, @PathVariable tripId: UUID) : ResponseEntity<Void> {
        val participation = participationService.getParticipation(userId, tripId)
                ?: return ResponseEntity(HttpStatus.NOT_FOUND)  // if participation not found, return 404

        participationService.declineParticipation(participation)
        tripService.refreshRemainingSeats(participation.trip)
        return ResponseEntity(HttpStatus.OK)
    }

    @DeleteMapping(path = ["/participations/{userId}/{tripId}"])
    fun deleteParticipation(@PathVariable userId: UUID, @PathVariable tripId: UUID) : ResponseEntity<Void> {
        if (participationService.deleteParticipation(userId, tripId))
            return ResponseEntity(HttpStatus.NO_CONTENT)

        val trip = tripService.getTrip(tripId)
                ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        tripService.refreshRemainingSeats(trip)

        return ResponseEntity(HttpStatus.NOT_FOUND)
    }
}