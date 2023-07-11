package com.group4.ticketingservice.service

import com.group4.ticketingservice.controller.SignUpRequest
import com.group4.ticketingservice.dto.UserDto

interface UserService {
    fun createUser(signUpRequest: SignUpRequest) : UserDto
}
