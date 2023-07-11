package com.group4.ticketingservice.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.group4.ticketingservice.dto.User
import com.group4.ticketingservice.repository.UserRepository

@Service
class UserService(private val userRepository: UserRepository) {
    @Transactional
    fun createUser(name: String, email: String): User {
        val user = User(name=name, email=email)
        return userRepository.save(User)
    }
}