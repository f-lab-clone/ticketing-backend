package com.group4.ticketingservice.event

import com.group4.ticketingservice.dto.EventSpecifications
import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.EventRepository
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.service.EventService
import com.group4.ticketingservice.utils.Authority
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
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
        authority = Authority.USER
    )
    private val sampleEvent: Event = Event(
        id = 1,
        title = "test title",
        date = OffsetDateTime.now(),
        reservationEndTime = OffsetDateTime.now(),
        reservationStartTime = OffsetDateTime.now(),
        maxAttendees = 10

    )
    val pageable: Pageable = PageRequest.of(1, 10)
    val title = "title"
    val content = mutableListOf(sampleEvent)
    val specification = EventSpecifications.withTitle(title)
    val page: Page<Event> = PageImpl(content, pageable, content.size.toLong())

    @Test
    fun `EventService_createEvent invoke EventRepository_findById`() {
        every { eventRepository.save(any()) } returns sampleEvent
        eventService.createEvent(
            title = sampleEvent.title,
            date = sampleEvent.date,
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
}
