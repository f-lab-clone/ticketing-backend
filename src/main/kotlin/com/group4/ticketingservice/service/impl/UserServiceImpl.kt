package com.group4.ticketingservice.service.impl

import com.group4.ticketingservice.controller.SignInRequest
import com.group4.ticketingservice.controller.SignUpRequest
import com.group4.ticketingservice.dto.UserDto
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.service.UserService
import com.group4.ticketingservice.utils.TokenProvider
import com.group4.ticketingservice.utils.toDto
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository,
                      private val tokenProvider: TokenProvider,
                      private val encoder: PasswordEncoder) : UserService {

    override fun createUser(request: SignUpRequest): UserDto {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email is already used")
        }

        val newUser = User.from(request,encoder)
        userRepository.save(newUser)

        return newUser.toDto()
    }

    override fun login(request: SignInRequest): UserDto {
        val user=userRepository.findByEmail(request.email)
                ?.takeIf { encoder.matches(request.password,it.password) }
                ?:throw IllegalArgumentException("아이디 또는 패스워드가 일치하지 않습니다.")

        val token=tokenProvider.createToken("${user.email}:${user.role}")
        val userDto=user.toDto()
        userDto.token=token

        return userDto
    }


}


