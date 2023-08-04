package com.group4.ticketingservice.dto

data class ErrorResponseDto(
        val errorCode: String,
        val message: String?
)