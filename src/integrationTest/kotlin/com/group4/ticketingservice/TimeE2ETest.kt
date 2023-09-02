package com.group4.ticketingservice

import com.group4.ticketingservice.config.ClockConfig
import com.group4.ticketingservice.config.GsonConfig
import com.group4.ticketingservice.config.WebConfig
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

@AutoConfigureMockMvc
@Import(ClockConfig::class, GsonConfig::class, WebConfig::class)
class TimeE2ETest @Autowired constructor(
    private val mockMvc: MockMvc
) : AbstractIntegrationTest() {

    @Test
    fun `All API only returns OffsetDateTime in UTC without any offset info`() {
        val eventCreateRequest = "{\"title\":\"test title\"," +
            "\"date\":\"2022-09-01T21:00:00.001+09:00\"," +
            "\"eventStartTime\":\"2022-09-01T22:00:00.001+09:00\"," +
            "\"eventEndTime\":\"2022-09-01T23:00:00.001+09:00\"," +
            "\"maxAttendees\":10}"

        mockMvc.perform(
            post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(eventCreateRequest)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.date").value("2022-09-01T12:00:00.001Z"))
            .andExpect(jsonPath("$.eventStartTime").value("2022-09-01T13:00:00.001Z"))
            .andExpect(jsonPath("$.eventEndTime").value("2022-09-01T14:00:00.001Z"))
    }
}
