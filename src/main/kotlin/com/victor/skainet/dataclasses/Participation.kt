package com.victor.skainet.dataclasses

import java.util.*

class Participation(
        override val id: UUID,
        val tripId: UUID,
        val userId: UUID
        ) : SkaiObject