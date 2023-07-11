package com.group4.ticketingservice.dto

data class UserCreateRequest(
    val name: String,
    val email: String
)

data class UserResponse(
    val id: Long,
    val name: String,
    val email: String
)
