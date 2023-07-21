package com.group4.ticketingservice.reservation

import com.group4.ticketingservice.controller.ReservationController
import com.group4.ticketingservice.entity.Reservation
import com.group4.ticketingservice.repository.ReservationRepository
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
    private lateinit var repository: ReservationRepository
    private val sampleReservation = Reservation(1)

    @Test
    fun `POST_api_reservation should invoke service_save`() {
        // given
        every { repository.save(sampleReservation) } returns sampleReservation

        // when
        mockMvc.perform(
            post("/reservation")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    """
            {
              "userId": ${sampleReservation.userId},
              "showId": ${sampleReservation.showId},
              "name": "${sampleReservation.name}"
            }
                    """.trimIndent()
                )
        )

        // then
        verify(exactly = 1) { repository.save(sampleReservation) }
    }

    @Test
    fun `POST_api_reservation should return saved reservation HTTP 200 Created`() {
        // given
        every { repository.save(sampleReservation) } returns sampleReservation

        // when
        val resultActions = mockMvc.perform(
            post("/reservation")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    """
            {
              "userId": ${sampleReservation.userId},
              "showId": ${sampleReservation.showId},
              "name": "${sampleReservation.name}"
            }
                    """.trimIndent()
                )
        )

        // then
        resultActions.andExpect(status().isOk)
    }
}
