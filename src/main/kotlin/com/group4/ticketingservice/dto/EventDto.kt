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
    val id: Long
)

data class EventResponse(
    val id: Long,
    val title: String,
    val date: OffsetDateTime,
    val reservationStartTime: OffsetDateTime,
    val reservationEndTime: OffsetDateTime,
    val maxAttendees: Int
)
