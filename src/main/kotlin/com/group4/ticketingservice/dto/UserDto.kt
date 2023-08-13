package com.group4.ticketingservice.dto

import java.time.LocalDateTime

class UserDto(
    val name: String,
    val email: String,
    val createdAt: LocalDateTime?
)
