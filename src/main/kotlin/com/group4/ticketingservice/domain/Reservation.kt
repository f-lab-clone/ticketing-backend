package com.group4.ticketingservice.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class Reservation(
    //        @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val userId: Int = 0,
    val showId: Int = 0,
    val name: String = "",
    val startTime: LocalDateTime = LocalDateTime.now(),
    val endTime: LocalDateTime = LocalDateTime.now(),
    val bookingTime: LocalDateTime = LocalDateTime.now()
)
