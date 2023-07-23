package com.group4.ticketingservice.service

import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {
    fun createUser(name: String, email: String): User {
        val user = User(name = name, email = email)
        return userRepository.save(user)
    }
}
