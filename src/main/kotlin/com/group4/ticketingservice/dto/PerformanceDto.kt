package com.group4.ticketingservice.dto

import java.time.OffsetDateTime

data class PerformanceCreateRequest(
    val title: String,
    val date: OffsetDateTime,
    val bookingStartTime: OffsetDateTime,
    val bookingEndTime: OffsetDateTime,
    val maxAttendees: Int
)

data class PerformanceDeleteRequest(
    val id: Long
)

data class PerformanceResponse(
    val id: Long,
    val title: String,
    val date: OffsetDateTime,
    val bookingStartTime: OffsetDateTime,
    val bookingEndTime: OffsetDateTime,
    val maxAttendees: Int
)
