package com.victor.skainet.controllers

import com.victor.skainet.dataclasses.*
import com.victor.skainet.services.ParticipationService
import com.victor.skainet.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin(origins = ["http://localhost:4200"], allowCredentials = "true")
@RestController
class UserController @Autowired constructor(
        val userService: UserService,
        val participationService: ParticipationService
){
    @GetMapping(path = ["/users"])
    fun getAllUsers() : Iterable<User> {
        return userService.getAllUsers()
    }

    @GetMapping(path = ["/users/{userId}"])
    fun getUser(@PathVariable userId : UUID) : ResponseEntity<User> {
        val user = userService.getUser(userId)
                ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        return ResponseEntity(user, HttpStatus.OK)
    }

    @GetMapping(path = ["/users/byusername/{username}"])
    fun getUserByUsername(@PathVariable username : String) : User? {
        return userService.getFromUsername(username)
    }

    @GetMapping(path = ["/users/{userId}/in-requests"])
    fun getIncomingRequests(@PathVariable userId: UUID) : Iterable<Participation> {
        return participationService.getParticipationRequestsFor(userId)
    }

    @GetMapping(path = ["/users/{userId}/out-requests"])
    fun getRequests(@PathVariable userId: UUID) : Iterable<Participation> {
        return participationService.getParticipationRequestsBy(userId)
    }

    @GetMapping(path = ["/users/{userId}/accepted"])
    fun getAcceptedTrips(@PathVariable userId: UUID) : Iterable<Trip> {
        return participationService.getTripsWithStatus(userId, Status.ACCEPTED)
    }

    @GetMapping(path = ["/users/{userId}/declined"])
    fun getDeclinedTrips(@PathVariable userId: UUID) : Iterable<Trip> {
        return participationService.getTripsWithStatus(userId, Status.DECLINED)
    }

    @GetMapping(path = ["/users/{userId}/waiting"])
    fun getWaitingTrips(@PathVariable userId: UUID) : Iterable<Trip> {
        return participationService.getTripsWithStatus(userId, Status.WAITING)
    }

    @PostMapping(path = ["/users"])
    fun addUser(@RequestBody user : User) : ResponseEntity<Void> {
        userService.addUser(user)

        return ResponseEntity( HttpStatus.OK)
    }

    @PostMapping(path = ["/authenticate"])
    fun authenticate(@RequestBody authenticationInfo : AuthenticationInfo) : ResponseEntity<User> {
        val username = authenticationInfo.username
        val password = authenticationInfo.password

        val user = userService.authenticate(username, password)
                ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)

        return ResponseEntity(user, HttpStatus.ACCEPTED)
    }
}