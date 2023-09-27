package com.group4.ticketingservice.dto

import java.time.OffsetDateTime
import jakarta.validation.constraints.NotNull

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
