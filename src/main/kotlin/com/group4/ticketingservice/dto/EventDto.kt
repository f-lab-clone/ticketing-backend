package com.group4.ticketingservice.dto

import java.time.OffsetDateTime

data class EventCreateRequest(
    val title: String,
    val date: OffsetDateTime,
    val reservationStartTime: OffsetDateTime,
    val reservationEndTime: OffsetDateTime,
    val maxAttendees: Int
)

data class EventDeleteRequest(
    val id: Int
)

data class EventResponse(
    val id: Int,
    val title: String,
    val date: OffsetDateTime,
    val reservationStartTime: OffsetDateTime,
    val reservationEndTime: OffsetDateTime,
    val maxAttendees: Int
)
