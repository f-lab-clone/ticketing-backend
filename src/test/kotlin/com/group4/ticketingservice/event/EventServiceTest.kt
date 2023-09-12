package com.group4.ticketingservice.event

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
import java.time.Duration
import java.time.OffsetDateTime
import java.util.*

class EventServiceTest {
    private val eventRepository: EventRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private val eventService: EventService = EventService(
        eventRepository = eventRepository
    )
    val sampleUserId = 1L

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
        reservationEndTime = OffsetDateTime.now() + Duration.ofHours(2),
        reservationStartTime = OffsetDateTime.now() + Duration.ofHours(1),
        maxAttendees = 10

    )

    @Test
    fun `EventService_getEvent invoke EventRepository_findById`() {
        every { eventRepository.findById(any()) } returns Optional.of(sampleEvent)
        eventService.getEvent(sampleEvent.id!!)
        verify(exactly = 1) { eventRepository.findById(any()) }
    }

    @Test
    fun `EventService_getEvents invoke EventRepository_findAll`() {
        every { eventRepository.findAll() } returns listOf(sampleEvent)
        eventService.getEvents()
        verify(exactly = 1) { eventRepository.findAll() }
    }
}
