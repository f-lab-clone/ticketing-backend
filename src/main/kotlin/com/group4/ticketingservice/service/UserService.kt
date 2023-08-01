package com.group4.ticketingservice.service


import com.group4.ticketingservice.dto.SignUpRequest
import com.group4.ticketingservice.dto.UserDto
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.utils.Authority

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository,
                  private val encoder: PasswordEncoder)  {

    fun createUser(request: SignUpRequest): UserDto {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email is already used")
        }

        val newUser = User(
                name = request.name,
                email = request.email,
                password = encoder.encode(request.password),
                authority = Authority.USER
        )


        userRepository.save(newUser)

        return User.toDto(newUser)

    }

}


