package com.group4.ticketingservice

import com.google.gson.Gson
import com.group4.ticketingservice.controller.SampleDTO
import com.group4.ticketingservice.controller.TestController
import com.group4.ticketingservice.utils.exception.ErrorCodes
import com.group4.ticketingservice.utils.exception.GlobalExceptionHandler
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ActiveProfiles("test")
class ResponseFormatTest {
    val mockMvc = MockMvcBuilders.standaloneSetup(TestController())
        .setControllerAdvice(GlobalExceptionHandler())
        .build()

    @Test
    fun `check if default 500 response and ErrorResponseDTO have the same format`() {
        val endpoint = "/test500"
        val mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/test500"))

        mvcResult.andExpect(MockMvcResultMatchers.status().isInternalServerError)
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(ErrorCodes.INTERNAL_SERVER_ERROR.errorCode))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.path").value(endpoint))
    }

    @Test
    fun `check NotSupportedType response and ErrorResponseDTO have the same format`() {
        val endpoint = "/test"
        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.post(endpoint)
                .content(Gson().toJson(mapOf(" " to " ")).toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )

        mvcResult.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType)
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(ErrorCodes.NOT_SUPPORTED_HTTP_MEDIA_TYPE.errorCode))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.path").value(endpoint))
    }

    @Test
    fun `check if default CustomException response and ErrorResponseDTO have the same format`() {
        val endpoint = "/test"
        val request = SampleDTO("sdfg")
        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.post(endpoint)
                .content(Gson().toJson(request).toString())
                .contentType(MediaType.APPLICATION_JSON)
        )

        mvcResult.andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(ErrorCodes.TEST_ERROR.errorCode))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.path").value(endpoint))
    }
}
