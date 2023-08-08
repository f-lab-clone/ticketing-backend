package com.group4.ticketingservice.user

import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.service.UserDetailService
import com.group4.ticketingservice.utils.Authority
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException

class UserDetailServiceTest {
    private val repository: UserRepository = mockk()
    private val userDetailService=UserDetailService(repository)

    val sampleUser = User(
            name = "minjun3021@qwer.com",
            email = "minjun",
            password = "1234",
            authority = Authority.USER
    )
    @Test
    fun `UserDetailService_loadUserByUsername invoke repository_findByEmail`() {
        every { repository.findByEmail(any()) } returns sampleUser

        // when
        userDetailService.loadUserByUsername(sampleUser.username)

        // then
        verify(exactly = 1) { repository.findByEmail(any()) }
    }

    @Test
    fun `UserDetailService_loadUserByUsername should return User when user is exist`() {
        every { repository.findByEmail(any()) } returns sampleUser

        // when
        val result=userDetailService.loadUserByUsername(sampleUser.username)

        // then
        Assertions.assertEquals(sampleUser.email,result.username )
    }

    fun `UserDetailService_loadUserByUsername should throw exception when user does not exist`() {
        every { repository.findByEmail(any()) } returns null

        // when
        userDetailService.loadUserByUsername(sampleUser.username)

        // then
        Assertions.assertThrows(UsernameNotFoundException::class.java) {
            userDetailService.loadUserByUsername(sampleUser.username)
        }
    }
}