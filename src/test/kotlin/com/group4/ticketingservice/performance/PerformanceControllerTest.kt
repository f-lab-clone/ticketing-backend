package com.group4.ticketingservice.performance

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.group4.ticketingservice.controller.PerformanceController
import com.group4.ticketingservice.dto.PerformanceCreateRequest
import com.group4.ticketingservice.dto.PerformanceDeleteRequest
import com.group4.ticketingservice.entity.Performance
import com.group4.ticketingservice.service.PerformanceService
import com.group4.ticketingservice.util.DateTimeConverter
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
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
    private val samplePerformanceDeleteRequest: PerformanceDeleteRequest = PerformanceDeleteRequest(
        id = 1
    )
    private val gson: Gson = GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, DateTimeConverter()).create()

    @Test
    fun `POST performances should return created performance`() {
        every { performanceService.createPerformance(any(), any(), any(), any(), any()) } returns samplePerformance

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
    }

    @Test
    fun `GET performances should return performance`() {
        every { performanceService.getPerformance(any()) } returns samplePerformance
        mockMvc.perform(
            get("/performances/${samplePerformance.id}")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(samplePerformance.id))
    }

    @Test
    fun `GET performances should return not found`() {
        every { performanceService.getPerformance(any()) } returns null
        mockMvc.perform(
            get("/performances/${samplePerformance.id}")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `GET List of performances should return list of performances`() {
        every { performanceService.getPerformances() } returns listOf(samplePerformance)
        mockMvc.perform(
            get("/performances/")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value(samplePerformance.id))
    }

    @Test
    fun `GET List of performances should return empty list`() {
        every { performanceService.getPerformances() } returns listOf()
        mockMvc.perform(
            get("/performances/")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNoContent)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isEmpty)
    }

    @Test
    fun `PUT performance should return updated performance`() {
        every {
            performanceService.updatePerformance(
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns samplePerformance
        mockMvc.perform(
            put("/performances/${samplePerformance.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(samplePerformanceCreateRequest))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(samplePerformance.id))
            .andExpect(jsonPath("$.title").value(samplePerformance.title))
            .andExpect(jsonPath("$.maxAttendees").value(samplePerformance.maxAttendees))
    }

    @Test
    fun `DELETE performance should return no content`() {
        every { performanceService.deletePerformance(any()) } returns Unit
        mockMvc.perform(
            delete("/performances/${samplePerformanceDeleteRequest.id}")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNoContent)
    }
}
