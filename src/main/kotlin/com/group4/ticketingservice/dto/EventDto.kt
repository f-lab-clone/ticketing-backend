package com.group4.ticketingservice.dto

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size
import java.time.OffsetDateTime

data class EventCreateRequest(
    @field:NotNull
    @field:Size(min = 1, max = 255)
    val title: String?,
    @field:NotNull
    @field:Future
    val startDate: OffsetDateTime?,
    @field:NotNull
    @field:Future
    val endDate: OffsetDateTime?,
    @field:NotNull
    @field:FutureOrPresent
    val reservationStartTime: OffsetDateTime?,
    @field:NotNull
    @field:Future
    val reservationEndTime: OffsetDateTime?,
    @field:NotNull
    @field:PositiveOrZero
    val maxAttendees: Int?
)

data class EventDeleteRequest(
    @field:NotNull
    @field:Positive
    val id: Int?
)

data class EventResponse(
    val id: Int,
    val title: String,
    val startDate: OffsetDateTime,
    val endDate: OffsetDateTime,
    val reservationStartTime: OffsetDateTime,
    val reservationEndTime: OffsetDateTime,
    val maxAttendees: Int
)
