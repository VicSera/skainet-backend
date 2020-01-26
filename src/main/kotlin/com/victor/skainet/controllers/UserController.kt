package com.victor.skainet.controllers

import com.victor.skainet.dataclasses.AuthenticationInfo
import com.victor.skainet.dataclasses.User
import com.victor.skainet.services.TripService
import com.victor.skainet.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.NoSuchElementException

@CrossOrigin(origins = ["http://localhost:4200"], allowCredentials = "true")
@RestController
class UserController @Autowired constructor(
        val userService: UserService,
        val tripService: TripService
){
    @GetMapping(path = ["/users"])
    fun getAllUsers() : Iterable<User> {
        println(userService.getAllUsers())
        return userService.getAllUsers()
    }

    @GetMapping(path = ["/users/{userId}"])
    fun getUser(@PathVariable userId : UUID) : ResponseEntity<User> {
        val user = userService.getUser(userId)
        if (user != null)
            return ResponseEntity(user, HttpStatus.OK)
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @GetMapping(path = ["/users/byusername/{username}"])
    fun getUserByUsername(@PathVariable username : String) : User? {
        return userService.getFromUsername(username)
    }

    @PostMapping(path = ["/users"])
    fun addUser(@RequestBody user : User) : ResponseEntity<User> {
        userService.addUser(user)

        return ResponseEntity(user, HttpStatus.OK)
    }

    @PostMapping(path = ["/authenticate"])
    fun authenticate(@RequestBody authenticationInfo : AuthenticationInfo) : ResponseEntity<User> {
        val username = authenticationInfo.username
        val password = authenticationInfo.password

        try {
            val user: User = userService.authenticate(username, password)
            return ResponseEntity(user, HttpStatus.ACCEPTED)
        }
        catch (excp : BadCredentialsException) {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }
}