package com.group4.ticketingservice.dto

data class SuccessResponseDTO(
        val success: Boolean=true,
        val message: String,
        val data: Any,

)



data class ErrorResponseDTO(
        val success: Boolean=false,
        val message: String,
        val errorCode: String,
        val errors : Any
)
