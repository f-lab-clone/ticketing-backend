package com.group4.ticketingservice

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.group4.ticketingservice.config.ClockConfig
import com.group4.ticketingservice.config.GsonConfig
import com.group4.ticketingservice.config.WebConfig
import com.group4.ticketingservice.dto.PerformanceCreateRequest
import com.group4.ticketingservice.dto.PerformanceResponse
import com.group4.ticketingservice.entity.Performance
import com.group4.ticketingservice.util.DateTimeConverter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Clock
import java.time.Duration
import java.time.OffsetDateTime

@AutoConfigureMockMvc
@Import(ClockConfig::class, GsonConfig::class, WebConfig::class)
class TimeE2ETest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val clock: Clock
) : AbstractIntegrationTest() {
    private val samplePerformance: Performance = Performance(
        id = 1,
        title = "test title",
        date = OffsetDateTime.now(clock),
        bookingEndTime = OffsetDateTime.now(clock) + Duration.ofHours(2),
        bookingStartTime = OffsetDateTime.now(clock) + Duration.ofHours(1),
        maxAttendees = 10
    )
    private val samplePerformanceCreateRequest: PerformanceCreateRequest = PerformanceCreateRequest(
        title = "test title",
        date = OffsetDateTime.now(clock),
        bookingEndTime = OffsetDateTime.now(clock) + Duration.ofHours(2),
        bookingStartTime = OffsetDateTime.now(clock) + Duration.ofHours(1),
        maxAttendees = 10
    )
    private val samplePerformanceResponse = PerformanceResponse(
        id = 1,
        title = "test title",
        date = OffsetDateTime.now(clock),
        bookingEndTime = OffsetDateTime.now(clock) + Duration.ofHours(2),
        bookingStartTime = OffsetDateTime.now(clock) + Duration.ofHours(1),
        maxAttendees = 10
    )

    private val gson: Gson = GsonBuilder().registerTypeAdapter(OffsetDateTime::class.java, DateTimeConverter()).create()

    @Test
    fun `POST performances should return created performance`() {
        val performanceCreateRequest = "{\"title\":\"test title\"," +
            "\"date\":\"2022-09-01T21:00:00.001+09:00\"," +
            "\"bookingStartTime\":\"2022-09-01T22:00:00.001+09:00\"," +
            "\"bookingEndTime\":\"2022-09-01T23:00:00.001+09:00\"," +
            "\"maxAttendees\":10}"

        mockMvc.perform(
            post("/performances")
                .contentType(MediaType.APPLICATION_JSON)
                .content(performanceCreateRequest)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.title").value(samplePerformanceCreateRequest.title))
            .andExpect(jsonPath("$.maxAttendees").value(samplePerformanceCreateRequest.maxAttendees))
            .andDo {
                Assertions.assertEquals(
                    gson.fromJson(it.response.contentAsString, PerformanceResponse::class.java),
                    samplePerformanceResponse
                )
            }
    }
}
