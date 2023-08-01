package com.group4.ticketingservice.dto

data class SignUpRequest(
    val email: String,
    val name: String,
    val password: String
)

class SignInRequest {
    var email: String = ""
    var password: String = ""
}
