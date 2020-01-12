package com.victor.skainet.repositories

import com.victor.skainet.dataclasses.User
import java.security.KeyException
import java.util.*

@org.springframework.stereotype.Repository
class InMemoryUserRepository {
    val contents : MutableMap<UUID, User> = mutableMapOf()

    fun add(newUser: User) {
        contents[newUser.id] = newUser
    }

    fun delete(id: UUID) : User? {
        try {
            val deletedUser : User? = contents[id]
            contents.remove(id)
            return deletedUser
        }
        catch (exception: KeyException) {
            throw RepositoryException("The given id (${id}) does not exist.")
        }
    }

    fun get(id: UUID): User? {
        val obj : User? = contents[id]

        if (obj != null) {
            return obj
        }
        throw RepositoryException("The given id (${id}) does not exist.")
    }

    fun getAll() : List<User> {
        return contents.values.toList()
    }
}