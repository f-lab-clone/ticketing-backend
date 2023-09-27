package com.group4.ticketingservice.dto

import jakarta.validation.constraints.NotNull
import java.time.OffsetDateTime

data class ReservationCreateRequest(
    @field:NotNull
    val eventId: Int?
)

data class ReservationUpdateRequest(
    @field:NotNull
    val eventId: Int?
)

data class ReservationDeleteRequest(
    @field:NotNull
    val id: Int?
)

data class ReservationResponse(
    val id: Int,
    val eventId: Int,
    val userId: Int,
    val bookedAt: OffsetDateTime
)
