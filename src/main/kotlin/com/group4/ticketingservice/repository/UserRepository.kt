package com.group4.ticketingservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import com.group4.ticketingservice.model.User

interface UserRepository: JpaRepository<User, Long>
