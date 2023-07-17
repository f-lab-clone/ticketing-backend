package com.group4.ticketingservice.controller

import com.google.gson.GsonBuilder
import com.group4.ticketingservice.dto.PerformanceCreateRequest
import com.group4.ticketingservice.entity.Performance
import com.group4.ticketingservice.service.PerformanceService
import com.group4.ticketingservice.util.LocalDateTimeConverter
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
import java.time.Duration
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
@WebMvcTest(PerformanceController::class)
class PerformanceControllerTest(
    @Autowired val mockMvc: MockMvc
) {
    @MockkBean
    private lateinit var performanceService: PerformanceService

    private val samplePerformance: Performance = Performance(
        id = 1,
        title = "test title",
        date = LocalDateTime.now(),
        bookingEndTime = LocalDateTime.now() + Duration.ofHours(2),
        bookingStartTime = LocalDateTime.now() + Duration.ofHours(1),
        maxAttendees = 10
    )
    private val samplePerformanceCreateRequest: PerformanceCreateRequest = PerformanceCreateRequest(
        title = "test title",
        date = LocalDateTime.now(),
        bookingEndTime = LocalDateTime.now() + Duration.ofHours(2),
        bookingStartTime = LocalDateTime.now() + Duration.ofHours(1),
        maxAttendees = 10
    )

    @Test
    fun `POST performances should return created performance`() {
        every { performanceService.createPerformance(any(), any(), any(), any(), any()) } returns samplePerformance
        val gson = GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeConverter()).create()

        mockMvc.perform(
            post("/performances")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(samplePerformanceCreateRequest))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(samplePerformance.id))
            .andExpect(jsonPath("$.title").value(samplePerformance.title))
            .andExpect(jsonPath("$.maxAttendees").value(samplePerformance.maxAttendees))
            .andDo { println(it) }
    }
}
