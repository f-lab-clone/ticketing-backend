package com.group4.ticketingservice.dto

import java.time.LocalDateTime

data class UserDto(
        val name: String,
        val email: String,
        val createdAt: LocalDateTime
)