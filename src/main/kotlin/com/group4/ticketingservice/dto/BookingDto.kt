package com.group4.ticketingservice.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class BookingCreateRequest(
    val performanceId: Long,
    val userId: Long
)

@Serializable
data class BookingUpdateRequest(
    val performanceId: Long
)

@Serializable
data class BookingResponse(
    val id: Long,
    val performanceId: Long,
    val userId: Long,
    val bookedAt: LocalDateTime
)
