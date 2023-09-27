package com.group4.ticketingservice.user

import com.google.gson.GsonBuilder
import com.group4.ticketingservice.config.SecurityConfig
import com.group4.ticketingservice.controller.UserController
import com.group4.ticketingservice.dto.SignUpRequest
import com.group4.ticketingservice.dto.UserDto
import com.group4.ticketingservice.filter.JwtAuthorizationEntryPoint
import com.group4.ticketingservice.service.UserService
import com.group4.ticketingservice.user.UserControllerTest.testFields.password
import com.group4.ticketingservice.user.UserControllerTest.testFields.testName
import com.group4.ticketingservice.user.UserControllerTest.testFields.testUserId
import com.group4.ticketingservice.user.UserControllerTest.testFields.testUserName
import com.group4.ticketingservice.utils.TokenProvider
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.OffsetDateTime

@WebMvcTest(
    controllers = [UserController::class],
    includeFilters = arrayOf(ComponentScan.Filter(value = [(TokenProvider::class), (SecurityConfig::class), (JwtAuthorizationEntryPoint::class)], type = FilterType.ASSIGNABLE_TYPE))

)
class UserControllerTest(
    @Autowired
    private val mockMvc: MockMvc
) {

    @MockkBean
    private lateinit var service: UserService

    object testFields {
        const val testName = "minjun"
        const val testUserId = 1
        const val testUserName = "minjun3021@qwer.com"
        const val testUserRole = "USER"
        const val password = "123456789"
    }

    val sampleSignUpRequest = SignUpRequest(
        email = testUserName,
        name = testName,
        password = password
    )
    val sampleUserDTO = UserDto(
        name = testName,
        email = testUserName,
        createdAt = OffsetDateTime.now()
    )

    val invalidSignUpRequest = SignUpRequest(
        email = "a",
        name = "a",
        password = "1234"
    )

    /**
     * Spring SecurityContext에 Authentication 객체를 넣어주는 커스텀 어노테이션을 만들어서
     * Controller가 Spring SecurityContext에 들어있는 유저 인증정보를 주입받는지를 확인할수있음
     */
    @Test
    @WithAuthUser(email = testUserName, id = testUserId)
    fun `GET_api_users_access_token_info should return userId injected by Spring Security with HTTP 200 OK`() {
        // when
        val resultActions: ResultActions =
            mockMvc.perform(MockMvcRequestBuilders.get("/users/access_token_info"))
        resultActions.andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.expires_in").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(testUserId))
    }

    @Test
    fun `POST_api_user should invoke service_create_user`() {
        // given
        every { service.createUser(sampleSignUpRequest) } returns sampleUserDTO

        // when
        mockMvc.perform(
            MockMvcRequestBuilders.post("/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(GsonBuilder().create().toJson(sampleSignUpRequest).toString())

        )

        // then
        verify(exactly = 1) { service.createUser(sampleSignUpRequest) }
    }

    @Test
    fun `POST_api_users should return 201 HttpStatus Code for unique user`() {
        // given
        every { service.createUser(any()) } returns sampleUserDTO

        // when
        val resultActions: ResultActions =
            mockMvc.perform(
                MockMvcRequestBuilders.post("/users/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GsonBuilder().create().toJson(sampleSignUpRequest).toString())

            )

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(testUserName))
    }

    @Test
    fun `POST_api_users should return 409 HttpStatus Code for Duplicate user`() {
        // given
        every { service.createUser(any()) } throws IllegalArgumentException()

        // when
        val resultActions: ResultActions =
            mockMvc.perform(
                MockMvcRequestBuilders.post("/users/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GsonBuilder().create().toJson(sampleSignUpRequest).toString())

            )

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isConflict)
    }

    @Test
    fun `POST_api_users should return 400 HttpStatus Code for invalid request`() {
        // when
        val resultActions: ResultActions =
            mockMvc.perform(
                MockMvcRequestBuilders.post("/users/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GsonBuilder().create().toJson(invalidSignUpRequest).toString())
            )
        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `POST_api_users should return errors array for invalid request`() {
        val resultActions: ResultActions =
            mockMvc.perform(
                MockMvcRequestBuilders.post("/users/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(GsonBuilder().create().toJson(invalidSignUpRequest).toString())
            )
        // then

        resultActions.andExpect(
            MockMvcResultMatchers.jsonPath("$.message").value(
                Matchers.containsString("name")

            )
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.message").value(
                Matchers.containsString("email")

            )
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.message").value(
                Matchers.containsString("password")

            )
        )
    }
}
