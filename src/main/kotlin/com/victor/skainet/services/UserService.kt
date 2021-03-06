package com.victor.skainet.services

import com.victor.skainet.dataclasses.User
import com.victor.skainet.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService @Autowired constructor(
        private val repository : UserRepository
) {
    fun addUser(user : User) {
        user.password = BCryptPasswordEncoder().encode(user.password)

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
                ?: return null

        return user.id
    }

    fun getFromUsername(username: String) : User? {
        return repository.getByUsername(username)
    }

    fun authenticate(username: String, password: String) : User? {
        val user = repository.getByUsername(username)
                ?: return null

        if (BCryptPasswordEncoder().matches(password, user.password))
            return user

        return null
    }
}