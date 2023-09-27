package com.group4.ticketingservice.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.time.OffsetDateTime

data class ReservationCreateRequest(
    @field:NotNull
    @field:Positive
    val eventId: Int?
)

data class ReservationUpdateRequest(
    @field:NotNull
    @field:Positive
    val eventId: Int?
)

data class ReservationDeleteRequest(
    @field:NotNull
    @field:Positive
    val id: Int?
)

data class ReservationResponse(
    val id: Int,
    val eventId: Int,
    val userId: Int,
    val bookedAt: OffsetDateTime
)
