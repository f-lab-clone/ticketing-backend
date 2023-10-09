package com.group4.ticketingservice.user

import com.group4.ticketingservice.dto.SignUpRequest
import com.group4.ticketingservice.dto.UserDto
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.service.UserService
import com.group4.ticketingservice.utils.Authority
import com.group4.ticketingservice.utils.exception.CustomException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.OffsetDateTime

class UserServiceTest {

    private val repository: UserRepository = mockk()
    private val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()
    private val userService: UserService = UserService(repository, passwordEncoder)

    val sampleSignUpRequest = SignUpRequest(
        email = "minjun3021@qwer.com",
        name = "minjun",
        password = "1234",
        phone = "010-1234-5678"
    )
    val sampleUserDTO = UserDto(
        name = "minjun3021@qwer.com",
        email = "minjun",
        createdAt = OffsetDateTime.now()
    )

    val sampleUser = User(
        name = "minjun3021@qwer.com",
        email = "minjun",
        password = "1234",
        authority = Authority.USER,
        phone = "010-1234-5678"
    )

    @Test
    fun `userService_createUser() invoke repository_save`() {
        every { repository.existsByEmail(any()) } returns false
        every { repository.save(any()) } returns sampleUser

        // when
        userService.createUser(sampleSignUpRequest)

        // then
        verify(exactly = 1) { repository.save(any()) }
    }

    @Test
    fun `userService_createUser() invoke encoder_encode`() {
        val encryptedPassword = passwordEncoder.encode(sampleSignUpRequest.password)
        val encoder: PasswordEncoder = mockk()
        val service = UserService(repository, encoder)

        every { repository.existsByEmail(any()) } returns false
        every { encoder.encode(any()) } returns encryptedPassword
        every { repository.save(any()) } returns sampleUser

        // when
        service.createUser(sampleSignUpRequest)

        // then
        verify(exactly = 1) { encoder.encode(any()) }
    }

    @Test
    fun `userService_create_user() return UserDto when request_email  not existed at repository`() {
        // given
        every { repository.existsByEmail(sampleSignUpRequest.email!!) } returns false
        every { repository.save(any()) } returns sampleUser

        // when
        val result = userService.createUser(sampleSignUpRequest)

        // then
        assertEquals(sampleSignUpRequest.email, result.email)
    }

    @Test
    fun `userService_create_user() throw Exception when request_email existed at repository`() {
        // given
        every { repository.existsByEmail(sampleSignUpRequest.email!!) } returns true

        // when
        // exception을 던져서 주석처리
        // userService.createUser(sampleSignUpRequest)

        // then
        assertThrows(CustomException::class.java) {
            userService.createUser(sampleSignUpRequest)
        }
    }
}
