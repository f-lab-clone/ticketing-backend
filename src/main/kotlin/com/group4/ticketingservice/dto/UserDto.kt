package com.group4.ticketingservice.dto

import java.time.OffsetDateTime

class UserDto(
    val name: String,
    val email: String,
    val createdAt: OffsetDateTime?
)
