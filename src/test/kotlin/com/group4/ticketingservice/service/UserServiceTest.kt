package com.group4.ticketingservice.service

import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class UserServiceTest {
    private val userRepository: UserRepository = mockk()
    private val userService = UserService(userRepository)
    private val sampleUser: User = User(name = "John Doe", email = "john@email.com")

    @Test
    fun `UserService_createUser invoke UserRepository_save`() {
        every { userRepository.save(any()) } returns sampleUser
        userService.createUser(sampleUser.name, sampleUser.email)
        verify(exactly = 1) { userRepository.save(any()) }
    }
}
