package com.group4.ticketingservice.health

import com.group4.ticketingservice.config.GsonConfig
import com.group4.ticketingservice.config.SecurityConfig
import com.group4.ticketingservice.controller.HealthController
import com.group4.ticketingservice.filter.JwtAuthorizationEntryPoint
import com.group4.ticketingservice.utils.OffsetDateTimeAdapter
import com.group4.ticketingservice.utils.TokenProvider
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(
    controllers = [HealthController::class],
    includeFilters = [ComponentScan.Filter(value = [(SecurityConfig::class), (GsonConfig::class), (OffsetDateTimeAdapter::class), (JwtAuthorizationEntryPoint::class), (TokenProvider::class)], type = FilterType.ASSIGNABLE_TYPE)]
)
class HealthControllerTest(@Autowired val mockMvc: MockMvc) {

    @Test
    fun `GET_api_root should return OK with HTTP 200 OK`() {
        // when
        val resultActions: ResultActions = mockMvc.perform(
            MockMvcRequestBuilders.get("/")
        )

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string("OK"))
    }

    @Test
    fun `GET_api_health should return OK with HTTP 200 OK`() {
        // when
        val resultActions: ResultActions = mockMvc.perform(
            MockMvcRequestBuilders.get("/health")
        )

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string("OK"))
    }
}
