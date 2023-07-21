package com.group4.ticketingservice.reservation

import com.group4.ticketingservice.controller.ReservationController
import com.group4.ticketingservice.dto.ReservationFromdto
import com.group4.ticketingservice.service.ReservationService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ReservationController::class)
class ReservationControllerTest(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    private lateinit var service: ReservationService
    private val sampleReservation = ReservationFromdto(1, 1, "hong")

    @Test
    fun `POST_api_reservation should invoke service_save`() {
        // given
        every { service.create(sampleReservation) } returns 1

        // when
        mockMvc.perform(
            post("/reservation")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    """
            {
                "name": "${sampleReservation.name}",
                "user_id": ${sampleReservation.user_id},
                "show_id": ${sampleReservation.show_id}
            }
                    """.trimIndent()
                )
        )

        // then
        verify(exactly = 1) { service.create(sampleReservation) }
    }

    @Test
    fun `POST_api_reservation should return saved reservation HTTP 200 Created`() {
        // given
        every { service.create(sampleReservation) } returns 1

        // when
        val resultActions = mockMvc.perform(
            post("/reservation")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    """
            {
                "name": "${sampleReservation.name}",
                "user_id": ${sampleReservation.user_id},
                "show_id": ${sampleReservation.show_id}
            }
                    """.trimIndent()
                )
        )

        // then
        resultActions.andExpect(status().isOk)
    }
}
