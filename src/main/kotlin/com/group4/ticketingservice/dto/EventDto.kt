package com.group4.ticketingservice.dto

import java.time.OffsetDateTime

data class EventCreateRequest(
    val name: String,
    val eventStartTime: OffsetDateTime,
    val eventEndTime: OffsetDateTime,
    val maxAttendees: Int
)

data class EventDeleteRequest(
    val id: Long
)

data class EventResponse(
    val id: Long,
    val name: String,
    val eventStartTime: OffsetDateTime,
    val eventEndTime: OffsetDateTime,
    val maxAttendees: Int
)
