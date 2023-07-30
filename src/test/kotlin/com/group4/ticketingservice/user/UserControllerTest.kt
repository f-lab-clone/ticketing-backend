package com.group4.ticketingservice.user

import com.group4.ticketingservice.JwtAuthorizationEntryPoint
import com.group4.ticketingservice.config.SecurityConfig
import com.group4.ticketingservice.controller.UserController
import com.group4.ticketingservice.dto.SignInRequest
import com.group4.ticketingservice.dto.SignUpRequest
import com.group4.ticketingservice.dto.UserDto
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.service.UserDetailService
import com.group4.ticketingservice.service.UserService
import com.group4.ticketingservice.user.UserControllerTest.testFields.testUserName
import com.group4.ticketingservice.user.UserControllerTest.testFields.testUserRole
import com.group4.ticketingservice.utils.Authority
import com.group4.ticketingservice.utils.TokenProvider
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.core.StringContains.containsString
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.yaml.snakeyaml.tokens.Token
import java.time.LocalDateTime

@WebMvcTest(controllers = [UserController::class],
        includeFilters = arrayOf(ComponentScan.Filter(value = [(TokenProvider::class),(SecurityConfig::class),(JwtAuthorizationEntryPoint::class),(TestUserDetailService::class)], type = FilterType.ASSIGNABLE_TYPE)),
        excludeFilters = arrayOf(ComponentScan.Filter(value = [(UserDetailService::class)], type = FilterType.ASSIGNABLE_TYPE))

)
class UserControllerTest(@Autowired
                        private val mockMvc: MockMvc) {


    @MockkBean
    private lateinit var service: UserService

    object testFields {
        const val testUserName = "qwer@asdf.com"
        const val testUserRole = "USER"
    }

    val sampleSignUpRequest = SignUpRequest(
            email = "minjun3021@qwer.com",
            name = "minjun",
            password = "1234"
    )
    val sampleUserDTO = UserDto(
            name = "minjun3021@qwer.com",
            email = "minjun",
            createdAt = LocalDateTime.now()
    )



    val sampleSignInRequest = SignInRequest().apply {
        email = "minjun3021@qwer.com"
        password = "1234"
    }

    /**
     * Spring SecurityContext에 Authentication 객체를 넣어주는 커스텀 어노테이션을 만들어서
     * Controller가 Spring SecurityContext에 들어있는 유저 인증정보를 주입받는지를 확인할수있음
     */
    @Test
    @WithAuthUser(email = testUserName, role = testUserRole)
    fun `GET_api_users_username should return username injected by Spring Security with HTTP 200 OK`() {
        // when
        val resultActions: ResultActions =
                mockMvc.perform(MockMvcRequestBuilders.get("/users/access_token_info"))
        resultActions.andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(testUserName))
    }

    @Test
    //Web
    fun `POST_api_users_login should return Bearer Token with HTTP 200 OK`() {

        // when
        val resultActions: ResultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/users/login")
                                .content(JSONObject(sampleSignInRequest).toString())
                                .contentType(MediaType.APPLICATION_JSON)
                )

        resultActions.andExpect(MockMvcResultMatchers.status().isOk)
        resultActions.andExpect(MockMvcResultMatchers.content().string(containsString("Bearer")))

    }

    @Test
    @WithMockUser
    fun `POST_api_user should invoke service_create_user`() {
        // given
        every { service.createUser(sampleSignUpRequest) } returns sampleUserDTO

        // when
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject(sampleSignUpRequest).toString())
                        .with(SecurityMockMvcRequestPostProcessors.csrf())

        )

        // then
        verify(exactly = 1) { service.createUser(sampleSignUpRequest) }
    }

}