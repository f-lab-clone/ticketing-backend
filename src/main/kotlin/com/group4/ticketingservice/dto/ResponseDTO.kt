package com.group4.ticketingservice.dto

import java.time.OffsetDateTime

data class SuccessResponseDTO(
    val timestamp: OffsetDateTime = OffsetDateTime.now(),
    val message: String = "success",
    val data: Any,
    val path: String

)

data class ErrorResponseDTO(
    val timestamp: OffsetDateTime = OffsetDateTime.now(),
    val errorCode: Int,
    val message: String,
    val path: String
)
