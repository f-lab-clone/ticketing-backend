package com.group4.ticketingservice.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.OffsetDateTime

@Entity
class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var title: String,

    var date: OffsetDateTime,

    var reservationStartTime: OffsetDateTime,

    var reservationEndTime: OffsetDateTime,

    var maxAttendees: Int,

    var currentReservationCount: Int = 0

)
