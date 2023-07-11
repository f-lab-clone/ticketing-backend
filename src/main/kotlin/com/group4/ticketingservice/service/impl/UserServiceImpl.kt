package com.group4.ticketingservice.service.impl

import com.group4.ticketingservice.controller.SignUpRequest
import com.group4.ticketingservice.dto.UserDto
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.service.UserService
import com.group4.ticketingservice.utils.toDto
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository,
                      private val encoder: PasswordEncoder) : UserService {

    override fun createUser(signUpRequest: SignUpRequest): UserDto {
        if (userRepository.existsByEmail(signUpRequest.email)) {
            throw IllegalArgumentException("Email is already used")
        }

        val newUser = User.from(signUpRequest,encoder)
        userRepository.save(newUser)

        return newUser.toDto()
    }


}


