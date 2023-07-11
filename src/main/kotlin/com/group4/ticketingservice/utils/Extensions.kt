package com.group4.ticketingservice.utils

import com.group4.ticketingservice.Authority
import com.group4.ticketingservice.controller.SignUpRequest
import com.group4.ticketingservice.dto.UserDto
import com.group4.ticketingservice.entity.User
import org.springframework.security.crypto.password.PasswordEncoder

//확장함수를 정의하는 클래스

fun User.toDto() = UserDto(
        name=name,
        email=email,
        createdAt=createdAt!!
)


