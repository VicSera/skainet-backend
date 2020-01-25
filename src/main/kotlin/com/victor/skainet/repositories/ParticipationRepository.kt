package com.victor.skainet.repositories

import com.victor.skainet.dataclasses.Participation
import com.victor.skainet.dataclasses.ParticipationKey
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ParticipationRepository : CrudRepository<Participation, ParticipationKey> {

}