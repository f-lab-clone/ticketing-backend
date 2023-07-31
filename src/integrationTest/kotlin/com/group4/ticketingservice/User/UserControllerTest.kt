package com.group4.ticketingservice.User


import com.group4.ticketingservice.AbstractIntegrationTest
import com.group4.ticketingservice.dto.SignInRequest
import com.group4.ticketingservice.dto.SignUpRequest
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.utils.Authority
import com.group4.ticketingservice.utils.TokenProvider
import org.hamcrest.core.StringContains
import org.json.JSONObject
import org.junit.Before
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.http.StreamingHttpOutputMessage.Body
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@AutoConfigureMockMvc
class UserControllerTest : AbstractIntegrationTest() {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var tokenProvider: TokenProvider

    object testFields {
        const val testName = "minjun"
        const val testUserName = "minjun3021@qwer.com"
        const val password = "1234"
    }

    val sampleSignUpRequest = SignUpRequest(
            email = testFields.testUserName,
            name = testFields.testName,
            password = testFields.password
    )

    val sampleSignInRequest = SignInRequest().apply {
        email = testFields.testUserName
        password = testFields.password
    }
    val sampleUser = User(
            name = testFields.testName,
            email = testFields.testUserName,
            password = BCryptPasswordEncoder().encode(testFields.password),
            authority = Authority.USER
    )


    @BeforeEach fun addUser() {
        userRepository.save(sampleUser)
    }

    @AfterEach fun removeUser() {
        userRepository.deleteAll()
    }


    fun getJwt(): String {
        val result = mockMvc.perform(
                MockMvcRequestBuilders.post("/users/login")
                        .content(JSONObject(sampleSignInRequest).toString())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn()
        val jwt=JSONObject(result.response.getContentAsString())
        return jwt.get("Authorization") as String
    }

    @Test
    fun `login success test`() {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/login")
                        .content(JSONObject(sampleSignInRequest).toString())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
                .andExpect(content().string(StringContains.containsString("Bearer")))

    }

    @Test
    fun `login failed test(password)`() {
        sampleSignInRequest.password = "4321" // wrong password
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/login")
                        .content(JSONObject(sampleSignInRequest).toString())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest)


    }

    @Test
    fun `login failed test(id)`() {
        sampleSignInRequest.email = "asdf@asdf.com" // wrong password
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/login")
                        .content(JSONObject(sampleSignInRequest).toString())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest)


    }

    @Test
    fun `access_token_info success test`() {
        val jwt=getJwt()

        val resultActions: ResultActions =
                mockMvc.perform(MockMvcRequestBuilders.get("/users/access_token_info")
                        .header("Authorization",jwt))
        resultActions.andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(testFields.testUserName))
    }

    @Test
    fun `access_token_info fail test(no token)`() {

        val resultActions: ResultActions =
                mockMvc.perform(MockMvcRequestBuilders.get("/users/access_token_info"))
        resultActions.andExpect(status().isUnauthorized)
    }

    @Test
    fun `access_token_info fail test(token Expired)`() {

        val jwt=tokenProvider.createWrongTokenForTest("${testFields.testUserName}:USER")
        val resultActions: ResultActions =
                mockMvc.perform(MockMvcRequestBuilders.get("/users/access_token_info")
                        .header("Authorization",jwt))
        resultActions.andExpect(status().isUnauthorized)
    }


}
