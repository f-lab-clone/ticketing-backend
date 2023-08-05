package com.group4.ticketingservice.dto

import java.time.OffsetDateTime

data class BookingCreateRequest(
    val performanceId: Long,
    val userId: Long
)

data class BookingUpdateRequest(
    val performanceId: Long
)

data class BookingDeleteRequest(
    val id: Long
)

data class BookingResponse(
    val id: Long,
    val performanceId: Long,
    val userId: Long,
    val bookedAt: OffsetDateTime
)
