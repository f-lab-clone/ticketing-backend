package com.group4.ticketingservice.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Reservation(
    //        @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    val userId: Int = 0,
    val showId: Int = 0,
    val name: String = "",
    val startTime: LocalDateTime? = null,
    val endTime: LocalDateTime? = null,
    val bookingTime: LocalDateTime? = null
)
