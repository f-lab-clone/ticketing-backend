package com.group4.ticketingservice.dto

import java.time.OffsetDateTime

data class SuccessResponseDTO(
    val timestamp: OffsetDateTime = OffsetDateTime.now(),
    val message: String = "success",
    var data: Any? = null,
    val path: String? = null
)

data class ErrorResponseDTO(
    val timestamp: OffsetDateTime = OffsetDateTime.now(),
    val errorCode: Int,
    val message: String,
    val path: String
)
