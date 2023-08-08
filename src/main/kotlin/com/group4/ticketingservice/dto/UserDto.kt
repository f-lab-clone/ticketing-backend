package com.group4.ticketingservice.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

class UserDto(
    val name: String,
    val email: String,
    val createdAt: LocalDateTime?
)