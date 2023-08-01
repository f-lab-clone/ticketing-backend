package com.group4.ticketingservice.service

import com.group4.ticketingservice.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailService(private val userRepository: UserRepository) :
    UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByEmail(username) ?: let { throw UsernameNotFoundException("User doesn't exist") }
    }
}
