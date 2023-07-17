package com.group4.ticketingservice.controller

import com.google.gson.Gson
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.service.UserService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(MockKExtension::class)
@WebMvcTest(UserController::class)
class UserControllerTest(
    @Autowired val mockMvc: MockMvc
) {
    @MockkBean
    private lateinit var userService: UserService

    private var sampleUser: User = User(
        id = 1,
        name = "test name",
        email = "test email"
    )

    @Test
    fun `POST users should return created user`() {
        every { userService.createUser(any(), any()) } returns sampleUser

        mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Gson().toJson(sampleUser))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(sampleUser.id))
            .andExpect(jsonPath("$.name").value(sampleUser.name))
            .andExpect(jsonPath("$.email").value(sampleUser.email))
    }
}
