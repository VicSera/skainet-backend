package com.victor.skainet.repositories

import com.victor.skainet.dataclasses.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CrudRepository<User, UUID> {
    fun getByUsername(username: String) : User?

    fun getById(id: UUID) : User?

    @Query(value = "select user from User user where user.id = :id")
    fun findByUUID(@Param("id") uuid: UUID) : User?
}