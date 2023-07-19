package com.group4.ticketingservice.dto

import java.time.LocalDateTime

data class PerformanceCreateRequest(
    val title: String,
    val date: LocalDateTime,
    val bookingStartTime: LocalDateTime,
    val bookingEndTime: LocalDateTime,
    val maxAttendees: Int
)

data class PerformanceDeleteRequest(
    val id: Long
)

data class PerformanceResponse(
    val id: Long,
    val title: String,
    val date: LocalDateTime,
    val bookingStartTime: LocalDateTime,
    val bookingEndTime: LocalDateTime,
    val maxAttendees: Int
)
