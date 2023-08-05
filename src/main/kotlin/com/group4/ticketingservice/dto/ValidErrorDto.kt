package com.group4.ticketingservice.dto

data class ValidErrorDto(
    val errorCode: String,
    val errors: MutableList<String>
)
