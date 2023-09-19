package com.group4.ticketingservice.reservation

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.group4.ticketingservice.JwtAuthorizationEntryPoint
import com.group4.ticketingservice.config.SecurityConfig
import com.group4.ticketingservice.controller.ReservationController
import com.group4.ticketingservice.dto.ReservationCreateRequest
import com.group4.ticketingservice.dto.ReservationDeleteRequest
import com.group4.ticketingservice.dto.ReservationResponse
import com.group4.ticketingservice.dto.ReservationUpdateRequest
import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.entity.Reservation
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.reservation.ReservationControllerTest.testFields.testUserId
import com.group4.ticketingservice.reservation.ReservationControllerTest.testFields.testUserName
import com.group4.ticketingservice.service.ReservationService
import com.group4.ticketingservice.user.WithAuthUser
import com.group4.ticketingservice.utils.Authority
import com.group4.ticketingservice.utils.TokenProvider
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.OffsetDateTime

@WebMvcTest(
    ReservationController::class,
    includeFilters = arrayOf(
        ComponentScan.Filter(value = [ (SecurityConfig::class), (TokenProvider::class), (JwtAuthorizationEntryPoint::class)], type = FilterType.ASSIGNABLE_TYPE)
    )
)
class ReservationControllerTest(
    @Autowired val mockMvc: MockMvc
) {
    @MockkBean
    private lateinit var reservationService: ReservationService
    object testFields {
        const val testName = "minjun"
        const val testUserName = "minjun3021@qwer.com"
        const val testUserId = 1
        const val testUserRole = "USER"
        const val password = "1234"
    }

    val sampleUser = User(
        name = testFields.testName,
        email = testFields.testUserName,
        password = testFields.password,
        authority = Authority.USER
    )

    private val sampleReservationCreateRequest = ReservationCreateRequest(
        eventId = 1
    )
    private val sampleReservationDeleteRequest = ReservationDeleteRequest(
        id = 1
    )

    private val sampleEvent: Event = Event(
        id = 1,
        title = "test title",
        date = OffsetDateTime.now(),
        reservationEndTime = OffsetDateTime.now(),
        reservationStartTime = OffsetDateTime.now(),
        maxAttendees = 10
    )
    private val sampleReservation: Reservation = Reservation(
        id = 1,
        user = sampleUser.apply { id = 1 },
        event = sampleEvent,
        bookedAt = OffsetDateTime.now()
    )

    private val gson: Gson = GsonBuilder().create()

    @Test
    @WithAuthUser(email = testUserName, id = testUserId)
    fun `POST reservations should return created reservation`() {
        every { reservationService.createReservation(1, 1) } returns sampleReservation
        val sampleReservationResponse = ReservationResponse(
            id = 1,
            userId = 1,
            eventId = 1,
            bookedAt = OffsetDateTime.now()
        )

        mockMvc.perform(
            post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Gson().toJson(sampleReservationCreateRequest))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(sampleReservation.id))
            .andExpect(jsonPath("$.userId").value(sampleReservation.user.id))
            .andExpect(jsonPath("$.eventId").value(sampleReservation.event.id))
    }

    @Test
    @WithAuthUser(email = testUserName, id = testUserId)
    fun `GET reservations should return reservation`() {
        every { reservationService.getReservation(1) } returns sampleReservation

        mockMvc.perform(
            get("/reservations/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(sampleReservation.id))
            .andExpect(jsonPath("$.userId").value(sampleReservation.user.id))
            .andExpect(jsonPath("$.eventId").value(sampleReservation.event.id))
    }

    @Test
    @WithAuthUser(email = testUserName, id = testUserId)
    fun `PUT reservations should return updated reservation`() {
        val reservationUpdateRequest = ReservationUpdateRequest(
            eventId = 2
        )
        val updatedReservation = Reservation(
            id = 1,
            user = sampleUser,
            event = Event(
                id = 2,
                title = "test title 2",
                date = OffsetDateTime.now(),
                reservationEndTime = OffsetDateTime.now(),
                reservationStartTime = OffsetDateTime.now(),
                maxAttendees = 10
            ),
            bookedAt = OffsetDateTime.now()
        )
        every { reservationService.updateReservation(1, 2) } returns updatedReservation

        mockMvc.perform(
            put("/reservations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Gson().toJson(reservationUpdateRequest))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(updatedReservation.id))
            .andExpect(jsonPath("$.userId").value(updatedReservation.user.id))
            .andExpect(jsonPath("$.eventId").value(updatedReservation.event.id))
    }

    @Test
    @WithAuthUser(email = testUserName, id = testUserId)
    fun `DELETE reservations should return no content`() {
        every { reservationService.deleteReservation(any(), any()) } returns Unit

        mockMvc.perform(
            delete("/reservations/${sampleReservationDeleteRequest.id}")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNoContent)
    }
}
