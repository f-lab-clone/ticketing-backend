package com.group4.ticketingservice.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.OffsetDateTime

data class EventCreateRequest(
    @field:NotNull
    @field:Size(min = 2, max = 255)
    val title: String?,
    @field:NotNull
    val date: OffsetDateTime?,
    @field:NotNull
    val reservationStartTime: OffsetDateTime?,
    @field:NotNull
    val reservationEndTime: OffsetDateTime?,
    @field:NotNull
    val maxAttendees: Int?
)

data class EventDeleteRequest(
    @field:NotNull
    val id: Int?
)

data class EventResponse(
    val id: Int,
    val title: String,
    val date: OffsetDateTime,
    val reservationStartTime: OffsetDateTime,
    val reservationEndTime: OffsetDateTime,
    val maxAttendees: Int
)
