package com.group4.ticketingservice.booking

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.group4.ticketingservice.config.ClockConfig
import com.group4.ticketingservice.controller.BookingController
import com.group4.ticketingservice.dto.BookingCreateRequest
import com.group4.ticketingservice.dto.BookingDeleteRequest
import com.group4.ticketingservice.dto.BookingResponse
import com.group4.ticketingservice.dto.BookingUpdateRequest
import com.group4.ticketingservice.entity.Booking
import com.group4.ticketingservice.entity.Performance
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.service.BookingService
import com.group4.ticketingservice.util.DateTimeConverter
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Clock
import java.time.Duration
import java.time.OffsetDateTime

@ExtendWith(MockKExtension::class)
@Import(ClockConfig::class)
@WebMvcTest(BookingController::class)
class BookingControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val clock: Clock
) {
    @MockkBean
    private lateinit var bookingService: BookingService

    private val sampleBookingCreateRequest = BookingCreateRequest(
        performanceId = 1,
        userId = 1
    )
    private val sampleBookingDeleteRequest = BookingDeleteRequest(
        id = 1
    )
    private val sampleUser: User = User(id = 1, name = "John Doe", email = "john@email.com")
    private val samplePerformance: Performance = Performance(
        id = 1,
        title = "test title",
        date = OffsetDateTime.now(clock),
        bookingEndTime = OffsetDateTime.now(clock) + Duration.ofHours(2),
        bookingStartTime = OffsetDateTime.now(clock) + Duration.ofHours(1),
        maxAttendees = 10
    )
    private val sampleBooking: Booking = Booking(
        id = 1,
        user = sampleUser,
        performance = samplePerformance,
        bookedAt = OffsetDateTime.now(clock)
    )

    private val gson: Gson = GsonBuilder().registerTypeAdapter(OffsetDateTime::class.java, DateTimeConverter()).create()

    @Test
    fun `POST bookings should return created booking`() {
        every { bookingService.createBooking(1, 1) } returns sampleBooking
        val sampleBookingResponse = BookingResponse(
            id = 1,
            userId = 1,
            performanceId = 1,
            bookedAt = OffsetDateTime.now(clock)
        )

        mockMvc.perform(
            post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Gson().toJson(sampleBookingCreateRequest))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(sampleBooking.id))
            .andExpect(jsonPath("$.userId").value(sampleBooking.user.id))
            .andExpect(jsonPath("$.performanceId").value(sampleBooking.performance.id))
            .andDo {
                assertEquals(
                    gson.fromJson(it.response.contentAsString, BookingResponse::class.java),
                    sampleBookingResponse
                )
            }


    }

    @Test
    fun `GET bookings should return booking`() {
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
    fun `PUT bookings should return updated booking`() {
        val bookingUpdateRequest = BookingUpdateRequest(
            performanceId = 2
        )
        val updatedBooking = Booking(
            id = 1,
            user = sampleUser,
            performance = Performance(
                id = 2,
                title = "test title 2",
                date = OffsetDateTime.now(clock),
                bookingEndTime = OffsetDateTime.now(clock) + Duration.ofHours(2),
                bookingStartTime = OffsetDateTime.now(clock) + Duration.ofHours(1),
                maxAttendees = 10
            ),
            bookedAt = OffsetDateTime.now(clock)
        )
        every { bookingService.updateBooking(1, 2) } returns updatedBooking

        mockMvc.perform(
            put("/bookings/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Gson().toJson(bookingUpdateRequest))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(updatedBooking.id))
            .andExpect(jsonPath("$.userId").value(updatedBooking.user.id))
            .andExpect(jsonPath("$.performanceId").value(updatedBooking.performance.id))
    }

    @Test
    fun `DELETE bookings should return no content`() {
        every { bookingService.deleteBooking(1) } returns Unit

        mockMvc.perform(
            delete("/bookings/${sampleBookingDeleteRequest.id}")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNoContent)
    }
}
