package com.victor.skainet.repositories

import com.victor.skainet.dataclasses.Participation
import com.victor.skainet.dataclasses.ParticipationKey
import org.springframework.data.repository.CrudRepository

interface ParticipationRepository : CrudRepository<Participation, ParticipationKey> {

}