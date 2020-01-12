package com.victor.skainet.repositories

import com.victor.skainet.dataclasses.SkaiObject
import java.lang.Exception
import java.util.*

class RepositoryException(override val message: String?) : Exception(message)

interface Repository {
    fun get(id : UUID) : SkaiObject
    fun add(newObject : SkaiObject)
    fun delete(id : UUID)
}