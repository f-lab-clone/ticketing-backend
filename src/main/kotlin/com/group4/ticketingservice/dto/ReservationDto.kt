package com.group4.ticketingservice.dto

import java.time.OffsetDateTime

data class ReservationCreateRequest(
    val eventId: Int
)

data class ReservationUpdateRequest(
    val eventId: Int
)

data class ReservationDeleteRequest(
    val id: Int
)

data class ReservationResponse(
    val id: Int,
    val eventId: Int,
    val userId: Int,
    val bookedAt: OffsetDateTime
)
