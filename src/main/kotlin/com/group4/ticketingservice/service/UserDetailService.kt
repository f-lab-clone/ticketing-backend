package com.group4.ticketingservice.service

import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.utils.exception.CustomException
import com.group4.ticketingservice.utils.exception.ErrorCodes
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailService(private val userRepository: UserRepository) :
    UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByEmail(username) ?: let { throw CustomException(ErrorCodes.USER_NOT_FOUND) }
    }
}
