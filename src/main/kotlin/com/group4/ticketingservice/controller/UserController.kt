package com.group4.ticketingservice.controller

import org.springframework.web.bind.annotation.RestController

@RestController(value = "/users")
class UserController {
}

data class SignUpRequest(val email: String,
                        val name: String,
                        val password: String)