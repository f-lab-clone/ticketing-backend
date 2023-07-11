package com.group4.ticketingservice.dto

import com.group4.ticketingservice.model.Performance
import com.group4.ticketingservice.model.User

data class BookingCreateRequest(
    val performance: Performance,
    val user: User,
    val seatIds: List<Long>
)

data class BookingUpdateRequest(
    val performanceId: Long
)

data class BookingResponse(
    val id: Long,
    val performance: Performance,
    val user: User,
    val seatIds: List<Long>,
    val bookedAt: LocalDateTime
)

