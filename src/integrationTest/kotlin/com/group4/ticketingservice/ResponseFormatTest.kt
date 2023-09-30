package com.group4.ticketingservice

import com.fasterxml.jackson.databind.ObjectMapper
import com.group4.ticketingservice.dto.ErrorResponseDTO
import com.group4.ticketingservice.utils.exception.ErrorCodes
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.HttpStatus
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@AutoConfigureMockMvc
@TestPropertySource(properties = ["spring.mvc.throw-exception-if-no-handler-found=true", "spring.web.resources.add-mappings=false"])
class ResponseFormatTest : AbstractIntegrationTest() {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `check if default 404 response and ErrorResponseDTO have the same format`() {
        val mvcResult = mockMvc.perform(get("/non-existing-endpoint"))
            .andReturn()

        val response = mvcResult.response

        assertEquals(HttpStatus.NOT_FOUND.value(), response.status)

        val errorResponse = objectMapper.readValue(response.contentAsString, ErrorResponseDTO::class.java)

        assertNotNull(errorResponse.timestamp)
        assertEquals(ErrorCodes.END_POINT_NOT_FOUND.errorCode, errorResponse.errorCode)
        assertNotNull(errorResponse.message)
        assertEquals("/non-existing-endpoint", errorResponse.path)
    }
}
