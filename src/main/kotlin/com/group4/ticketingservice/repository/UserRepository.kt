package com.group4.ticketingservice.repository

import com.group4.ticketingservice.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): User?
}
