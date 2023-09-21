package com.group4.ticketingservice.dto

import java.time.OffsetDateTime

data class SuccessResponseDTO(
    val success: Boolean = true,
    val message: String,
    val data: Any

)

data class ErrorResponseDTO(
    val timestamp: OffsetDateTime = OffsetDateTime.now(),
    val errorCode: Int,
    val message: String,
    val path: String
)
