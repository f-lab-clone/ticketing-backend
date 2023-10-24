package com.group4.ticketingservice.event

import com.group4.ticketingservice.config.SecurityConfig
import com.group4.ticketingservice.controller.EventController
import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.filter.JwtAuthorizationEntryPoint
import com.group4.ticketingservice.service.EventService
import com.group4.ticketingservice.utils.TokenProvider
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Duration
import java.time.OffsetDateTime

@ExtendWith(MockKExtension::class)
@WebMvcTest(
    EventController::class,
    includeFilters = arrayOf(
        ComponentScan.Filter(value = [(SecurityConfig::class), (TokenProvider::class), (JwtAuthorizationEntryPoint::class)], type = FilterType.ASSIGNABLE_TYPE)
    )
)
class EventControllerTest(
    @Autowired val mockMvc: MockMvc
) {
    @MockkBean
    private lateinit var eventService: EventService

    object testFields {
        const val testName = "minjun"
        const val testUserId = 1
        const val testUserName = "minjun3021@qwer.com"
        const val testUserRole = "USER"
        const val password = "123456789"
    }

    val sampleUser = User(
        name = "james",
        email = "james@example.com",
        password = "12345678",

        phone = "010-1234-5678"
    )
    private val sampleEvent: Event = Event(
        id = 1,
        name = "test title",
        startDate = OffsetDateTime.now(),
        endDate = OffsetDateTime.now(),
        reservationEndTime = OffsetDateTime.now(),
        reservationStartTime = OffsetDateTime.now(),
        maxAttendees = 10

    )

    val pageable: Pageable = PageRequest.of(0, 4)
    val content = mutableListOf(
        Event(
            id = 2,
            name = "민준이의 전국군가잘함",
            startDate = OffsetDateTime.now(),
            endDate = OffsetDateTime.now(),
            reservationEndTime = OffsetDateTime.now() + Duration.ofHours(2),
            reservationStartTime = OffsetDateTime.now() - Duration.ofHours(2),
            maxAttendees = 10
        ),
        Event(
            id = 1,
            name = "정섭이의 코딩쇼",
            startDate = OffsetDateTime.now(),
            endDate = OffsetDateTime.now(),
            reservationEndTime = OffsetDateTime.now() + Duration.ofHours(2),
            reservationStartTime = OffsetDateTime.now() - Duration.ofHours(2),
            maxAttendees = 10
        ),
        Event(
            id = 4,
            name = "준하의 스파르타 코딩 동아리 설명회",
            startDate = OffsetDateTime.now(),
            endDate = OffsetDateTime.now(),
            reservationEndTime = OffsetDateTime.now() + Duration.ofHours(2),
            reservationStartTime = OffsetDateTime.now() - Duration.ofHours(2),
            maxAttendees = 10
        ),
        Event(
            id = 3,
            name = "하영이의 신작도서 팬싸인회",
            startDate = OffsetDateTime.now(),
            endDate = OffsetDateTime.now(),
            reservationEndTime = OffsetDateTime.now() + Duration.ofHours(2),
            reservationStartTime = OffsetDateTime.now() - Duration.ofHours(2),
            maxAttendees = 10
        )
    )

    val DESCENDING = "desc"
    val ASCENDING = "asc"
    val SORT_BY_DEADLINE = "deadline"
    val SORT_BY_START_DATE = "startDate"
    val SORT_BY_CREATED_AT = "createdAt"

    val page: Page<Event> = PageImpl(content)
    val emptyPage: Page<Event> = PageImpl(listOf(), pageable, listOf<Event>().size.toLong())

    @Test
    fun `POST events should return created event`() {
        every { eventService.createEvent(any(), any(), any(), any(), any(), any()) } returns sampleEvent

        val eventCreateRequest = "{\"name\":\"test title\"," +
            "\"startDate\":\"2044-02-04T21:00:00.001+09:00\"," +
            "\"endDate\":\"2044-02-04T21:00:00.001+09:00\"," +
            "\"reservationStartTime\":\"2044-01-01T22:00:00.001+09:00\"," +
            "\"reservationEndTime\":\"2044-01-01T23:00:00.001+09:00\"," +
            "\"maxAttendees\":10}"

        mockMvc.perform(
            post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(eventCreateRequest)
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.data.id").value(sampleEvent.id))
            .andExpect(jsonPath("$.data.name").value(sampleEvent.name))
            .andExpect(jsonPath("$.data.maxAttendees").value(sampleEvent.maxAttendees))
    }

    @Test
    fun `GET events should return event`() {
        every { eventService.getEvent(any()) } returns sampleEvent
        mockMvc.perform(
            get("/events/${sampleEvent.id}")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.data.id").value(sampleEvent.id))
    }

    @Test
    fun `GET events should return not found`() {
        every { eventService.getEvent(any()) } returns null
        mockMvc.perform(
            get("/events/${sampleEvent.id}")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `GET List of events should return 400 bad request when request invalid sort`() {
        // Given
        every { eventService.getEvents(any(), any(), any()) } returns page

        // When
        val result = mockMvc.perform(
            get("/events")
                .param("sort", "$SORT_BY_DEADLINE,$DESCENDING")
        )

        // Then
        result
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errorCode").value(10004))
    }

    @Test
    fun `GET List of events should return list of events when request valid sort`() {
        // Given
        every { eventService.getEvents(any(), any(), any()) } returns page

        // When
        val result = mockMvc.perform(
            get("/events")
                .param("sort", "$SORT_BY_DEADLINE,$ASCENDING")
        )
        // Then
        result
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.data.[0].id").value(2))
            .andExpect(jsonPath("$.data.[1].id").value(1))
    }

    @Test
    fun `GET List of events should return list of events when request valid sort and last access id and time`() {
        // Given
        every { eventService.getEvents(any(), any(), any()) } returns page

        // When
        val result = mockMvc.perform(
            get("/events")
                .param("sort", "$SORT_BY_DEADLINE,$ASCENDING")
                .param("time", "2024-10-19T00:46:00.123123Z")
                .param("id", "3538937")
        )
        // Then
        result
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.data.[0].id").value(2))
            .andExpect(jsonPath("$.data.[1].id").value(1))
    }

    @Test
    fun `GET List of events should return empty list`() {
        every { eventService.getEvents(any(), any(), any()) } returns emptyPage
        mockMvc.perform(
            get("/events")
                .param("sort", SORT_BY_DEADLINE)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.data").isEmpty)
    }
}
