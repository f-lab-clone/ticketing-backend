package com.group4.ticketingservice

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.group4.ticketingservice.TimeE2ETest.testFields.password
import com.group4.ticketingservice.TimeE2ETest.testFields.testName
import com.group4.ticketingservice.TimeE2ETest.testFields.testUserName
import com.group4.ticketingservice.config.GsonConfig
import com.group4.ticketingservice.config.WebConfig
import com.group4.ticketingservice.dto.SignInRequest
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.utils.Authority
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@Import(GsonConfig::class, WebConfig::class)
class TimeE2ETest @Autowired constructor(
    val userRepository: UserRepository,
    private val mockMvc: MockMvc
) : AbstractIntegrationTest() {
    object testFields {
        const val testUserId = 1
        const val testName = "minjun"
        const val testUserName = "minjun3021@qwer.com"
        const val password = "1234"
    }

    val sampleSignInRequest = SignInRequest().apply {
        email = testUserName
        password = password
    }
    val sampleUser = User(
        name = testName,
        email = testUserName,
        password = password,
        authority = Authority.USER
    )

    @BeforeEach
    fun addUser() {
        userRepository.save(sampleUser)
    }

    @AfterEach
    fun removeUser() {
        userRepository.deleteAll()
    }

    @Test
    fun `All API only returns OffsetDateTime in UTC without any offset info`() {
        val eventCreateRequest = "{\"title\":\"test title\"," +
            "\"date\":\"2022-09-01T21:00:00.001+09:00\"," +
            "\"reservationStartTime\":\"2022-09-01T22:00:00.001+09:00\"," +
            "\"reservationEndTime\":\"2022-09-01T23:00:00.001+09:00\"," +
            "\"maxAttendees\":10}"
        val jwt = getJwt()
        mockMvc.perform(
            post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(eventCreateRequest)
                .header("Authorization", jwt)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.date").value("2022-09-01T12:00:00.001Z"))
            .andExpect(jsonPath("$.reservationStartTime").value("2022-09-01T13:00:00.001Z"))
            .andExpect(jsonPath("$.reservationEndTime").value("2022-09-01T14:00:00.001Z"))
    }

    fun getJwt(): String {
        val gson = GsonBuilder().create()

        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/users/signin")
                .content(gson.toJson(sampleSignInRequest).toString())
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn()
        val jwt = gson.fromJson(result.response.contentAsString, JsonObject::class.java)
        return jwt.get("Authorization").asString
    }
}
