package com.group4.ticketingservice.service


import com.group4.ticketingservice.dto.SignInRequest
import com.group4.ticketingservice.dto.SignUpRequest
import com.group4.ticketingservice.dto.UserDto
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.utils.TokenProvider
import com.group4.ticketingservice.utils.toDto
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository,
                  private val tokenProvider: TokenProvider,
                  private val encoder: PasswordEncoder)  {

    fun createUser(request: SignUpRequest): UserDto {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email is already used")
        }

        val newUser = User.from(request,encoder)
        userRepository.save(newUser)

        return newUser.toDto()
    }

}


