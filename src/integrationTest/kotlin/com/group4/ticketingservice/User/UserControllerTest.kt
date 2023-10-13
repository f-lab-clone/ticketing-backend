package com.group4.ticketingservice.User

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.group4.ticketingservice.AbstractIntegrationTest
import com.group4.ticketingservice.dto.SignInRequest
import com.group4.ticketingservice.dto.SignUpRequest
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.utils.TokenProvider
import org.hamcrest.core.StringContains
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@TestPropertySource(properties = ["spring.jpa.hibernate.ddl-auto=create"])
@AutoConfigureMockMvc
class UserControllerTest : AbstractIntegrationTest() {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var tokenProvider: TokenProvider

    object testFields {
        const val testUserId = 1
        const val testName = "minjun"
        const val testUserName = "minjun3021@qwer.com"
        const val password = "1234"
    }

    val sampleSignUpRequest = SignUpRequest(
        email = testFields.testUserName,
        name = testFields.testName,
        password = testFields.password,
        phoneNumber = "010-1234-5678"
    )

    val sampleSignInRequest = SignInRequest().apply {
        email = testFields.testUserName
        password = testFields.password
    }
    val sampleUser = User(
        name = testFields.testName,
        email = testFields.testUserName,
        password = BCryptPasswordEncoder().encode(testFields.password),

        phone = "010-1234-5678"
    )

    @BeforeEach fun addUser() {
        userRepository.save(sampleUser)
    }

    @AfterEach fun removeUser() {
        userRepository.deleteAll()
    }

    fun getJwt(): String {
        val gson = GsonBuilder().create()

        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/users/signin")
                .content(gson.toJson(sampleSignInRequest).toString())
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn()
        val jwt = gson.fromJson(result.response.contentAsString, JsonObject::class.java)
        return (jwt.get("data") as JsonObject).get("Authorization").asString
    }

    @Test
    fun `POST_api_users_login should return jwt with HTTPStatus 200 OK`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/users/signin")
                .content(GsonBuilder().create().toJson(sampleSignInRequest).toString())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
            .andExpect(content().string(StringContains.containsString("Bearer")))
    }

    @Test
    fun `POST_api_users_login should return HTTPStatus 401 Unauthorized when password is invalid`() {
        sampleSignInRequest.password = "4321" // wrong password
        mockMvc.perform(
            MockMvcRequestBuilders.post("/users/signin")
                .content(GsonBuilder().create().toJson(sampleSignInRequest).toString())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnauthorized)
    }

    @Test
    fun `POST_api_users_login should return HTTPStatus 401 Unauthorized when user doesn't exist`() {
        sampleSignInRequest.email = "asdf@asdf.com" // user doesn't exist
        mockMvc.perform(
            MockMvcRequestBuilders.post("/users/signin")
                .content(GsonBuilder().create().toJson(sampleSignInRequest).toString())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnauthorized)
    }

    @Test
    fun `GET_api_users_access_token_info should return expires_in with HTTPStatus 200 OK`() {
        val jwt = getJwt()

        val resultActions: ResultActions =
            mockMvc.perform(
                MockMvcRequestBuilders.get("/users/access_token_info")
                    .header("Authorization", jwt)
            )
        resultActions.andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.expires_in").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").exists())
    }

    @Test
    fun `GET_api_users_access_token_info should return HTTPStatus 401 Unauthorized when header not exist`() {
        val resultActions: ResultActions =
            mockMvc.perform(MockMvcRequestBuilders.get("/users/access_token_info"))
        resultActions.andExpect(status().isUnauthorized)
    }
}
