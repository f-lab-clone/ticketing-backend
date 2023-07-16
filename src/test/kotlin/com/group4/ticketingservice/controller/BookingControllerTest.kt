package com.group4.ticketingservice.controller

import com.group4.ticketingservice.dto.BookingCreateRequest
import com.group4.ticketingservice.dto.BookingUpdateRequest
import com.group4.ticketingservice.entity.Booking
import com.group4.ticketingservice.entity.Performance
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.service.BookingService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.junit5.MockKExtension
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockKExtension::class)
@WebMvcTest(BookingController::class)
class BookingControllerTest(
    @Autowired val mockMvc: MockMvc,
) {
    @MockkBean
    private lateinit var bookingService: BookingService

    private val sampleBookingCreateRequest = BookingCreateRequest(
        performanceId = 1,
        userId = 1
    )
    private val sampleUser: User = User(id = 1, name = "John Doe", email = "john@email.com")
    private val samplePerformance: Performance = Performance(
        id = 1,
        title = "test title",
        date = LocalDateTime.now(),
        bookingEndTime = LocalDateTime.now() + Duration.ofHours(2),
        bookingStartTime = LocalDateTime.now() + Duration.ofHours(1),
        maxAttendees = 10
    )
    private val sampleBooking: Booking = Booking(
        id = 1,
        user = sampleUser,
        performance = samplePerformance
    )

    @Test
    fun createBooking() {
        every { bookingService.createBooking(1, 1) } returns sampleBooking

        mockMvc.perform(
            post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.encodeToString(sampleBookingCreateRequest))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(sampleBooking.id))
            .andExpect(jsonPath("$.userId").value(sampleBooking.user.id))
            .andExpect(jsonPath("$.performanceId").value(sampleBooking.performance.id))
    }

    @Test
    fun getBooking() {
        every { bookingService.getBooking(1) } returns sampleBooking

        mockMvc.perform(
            get("/bookings/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(sampleBooking.id))
            .andExpect(jsonPath("$.userId").value(sampleBooking.user.id))
            .andExpect(jsonPath("$.performanceId").value(sampleBooking.performance.id))
    }

    @Test
    fun updateBooking() {
        val bookingUpdateRequest = BookingUpdateRequest(
            performanceId = 2
        )
        val updatedBooking = Booking(
            id = 1,
            user = sampleUser,
            performance = Performance(
                id = 2,
                title = "test title 2",
                date = LocalDateTime.now(),
                bookingEndTime = LocalDateTime.now() + Duration.ofHours(2),
                bookingStartTime = LocalDateTime.now() + Duration.ofHours(1),
                maxAttendees = 10
            )
        )
        every { bookingService.updateBooking(1, 2) } returns updatedBooking

        mockMvc.perform(
            put("/bookings/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.encodeToString(bookingUpdateRequest))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(updatedBooking.id))
            .andExpect(jsonPath("$.userId").value(updatedBooking.user.id))
            .andExpect(jsonPath("$.performanceId").value(updatedBooking.performance.id))
    }


    @Test
    fun deleteBooking() {
        every { bookingService.deleteBooking(1) } returns Unit

        mockMvc.perform(
            delete("/bookings/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNoContent)
    }
}