package com.group4.ticketingservice.event

import com.group4.ticketingservice.dto.EventSpecifications
import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.EventRepository
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.service.EventService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import java.time.Duration
import java.time.OffsetDateTime
import java.util.Optional

class EventServiceTest {
    private val eventRepository: EventRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private val eventService: EventService = EventService(
        eventRepository = eventRepository
    )
    val sampleUserId = 1

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

    val pageable: Pageable = PageRequest.of(0, 4, Sort.by("title").ascending())
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
    val totalElements: Long = 100
    val page: Page<Event> = PageImpl(content, pageable, totalElements)
    val emptyPage: Page<Event> = PageImpl(listOf(), pageable, listOf<Event>().size.toLong())

    val title = "코딩"
    val specification = EventSpecifications.withTitle(title)

    @Test
    fun `EventService_createEvent invoke EventRepository_findById`() {
        every { eventRepository.save(any()) } returns sampleEvent
        eventService.createEvent(
            title = sampleEvent.name,
            startDate = sampleEvent.startDate,
            endDate = sampleEvent.startDate,
            reservationStartTime = sampleEvent.reservationStartTime,
            reservationEndTime = sampleEvent.reservationEndTime,
            maxAttendees = sampleEvent.maxAttendees
        )
        verify(exactly = 1) { eventRepository.save(any()) }
    }

    @Test
    fun `EventService_getEvent invoke EventRepository_findById`() {
        every { eventRepository.findById(any()) } returns Optional.of(sampleEvent)
        eventService.getEvent(sampleEvent.id!!)

        verify(exactly = 1) { eventRepository.findById(any()) }
    }

    @Test
    fun `EventService_getEvents invoke EventRepository_findAll`() {
        every { eventRepository.findAll(any(), pageable) } returns page
        eventService.getEvents(title, pageable)
        verify(exactly = 1) { eventRepository.findAll(any(), pageable) }
    }

    @Test
    fun `EventService_getEvents return page with pagination and sorting`() {
        // Given
        every { eventRepository.findAll(any(), pageable) } returns page

        // When
        val result: Page<Event> = eventService.getEvents(null, pageable)

        // Then
        assertThat(result.totalElements).isEqualTo(totalElements)
        assertThat(result.numberOfElements).isEqualTo(content.size)
        assertThat(result.content[0].id).isEqualTo(content[0].id)
        assertThat(result.content[1].id).isEqualTo(content[1].id)
    }
}
