package com.victor.skainet.services

import com.victor.skainet.dataclasses.User
import com.victor.skainet.repositories.InMemoryUserRepository
import com.victor.skainet.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException

@Service
class UserService @Autowired constructor(
        private val repository : UserRepository
) {
    init {
        println(repository.findAll())
    }

    fun addUser(user : User) {
        repository.save(user)
    }

    fun deleteUser(userId : UUID) {
        repository.deleteById(userId)
    }

    fun getUser(userId : UUID) : User? {
        return repository.findById(userId).orElse(null)
    }

    fun getAllUsers() : Iterable<User> {
        return repository.findAll()
    }

    fun getIdFromUsername(username : String) : UUID? {
        val user = repository.getByUsername(username)

        if (user != null)
            return user.id

        return null
    }

    fun getFromUsername(username: String) : User? {
        return repository.getByUsername(username)
    }

    fun authenticate(username: String, password: String) : User {
        val user = repository.getByUsername(username)

        if (user != null && user.password == password)
            return user
        else
            throw BadCredentialsException("Invalid Credentials")
    }
}