package com.group4.ticketingservice.dto

import java.time.OffsetDateTime

data class ReservationCreateRequest(
    val eventId: Long,
    val userId: Long
)

data class ReservationUpdateRequest(
    val eventId: Long
)

data class ReservationDeleteRequest(
    val id: Long
)

data class ReservationResponse(
    val id: Long,
    val eventId: Long,
    val userId: Long,
    val bookedAt: OffsetDateTime
)
