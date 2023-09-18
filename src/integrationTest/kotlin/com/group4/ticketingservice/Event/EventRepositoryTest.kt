package com.group4.ticketingservice.Event

import com.group4.ticketingservice.AbstractIntegrationTest
import com.group4.ticketingservice.Reservation.ReservationTest
import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.EventRepository
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.utils.Authority
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.Duration.ofHours
import java.time.OffsetDateTime
import java.time.ZoneOffset

class EventRepositoryTest @Autowired constructor(
    @Autowired val eventRepository: EventRepository,
    @Autowired val userRepository: UserRepository
) : AbstractIntegrationTest() {

    val sampleUser = User(
        name = ReservationTest.testFields.testName,
        email = ReservationTest.testFields.testUserName,
        password = BCryptPasswordEncoder().encode(ReservationTest.testFields.password),
        authority = Authority.USER
    )

    @BeforeEach fun saveUser() {
        userRepository.save(sampleUser)
    }

    @Test
    fun `EventRepository_save should return savedEvent`() {
        // given
        val now = OffsetDateTime.now(ZoneOffset.UTC)
        val sampleEvent = Event(
            title = "test title",
            date = now,
            reservationEndTime = now + ofHours(2),
            reservationStartTime = now + ofHours(1),
            maxAttendees = 10

        )

        // when
        val savedEvent = eventRepository.save(sampleEvent)

        // then
        assertThat(savedEvent).isEqualTo(sampleEvent)
    }

    @Test
    fun `EventRepository_findByIdOrNull should return event`() {
        // given
        val now = OffsetDateTime.now(ZoneOffset.UTC)
        val sampleEvent = Event(
            title = "test title",
            date = now,
            reservationEndTime = now + ofHours(2),
            reservationStartTime = now + ofHours(1),
            maxAttendees = 10

        )
        val savedEvent = eventRepository.save(sampleEvent)

        // when
        val foundEvent: Event? = eventRepository.findByIdOrNull(
            savedEvent.id!!
        )

        // then
        assert(foundEvent?.id == sampleEvent.id)
    }

    @Test
    fun `EventRepository_findAll should return list of events`() {
        // given
        val now = OffsetDateTime.now(ZoneOffset.UTC)
        val sampleEvent = Event(
            title = "test title",
            date = now,
            reservationEndTime = now + ofHours(2),
            reservationStartTime = now + ofHours(1),
            maxAttendees = 10

        )
        eventRepository.save(sampleEvent)

        // when
        val events = eventRepository.findAll()

        // then
        assertInstanceOf(List::class.java, events)
        assert(events.size > 0)
    }

    @Test
    fun `EventRepository_delete should delete event`() {
        // given
        val now = OffsetDateTime.now(ZoneOffset.UTC)
        val sampleEvent = Event(
            title = "test title",
            date = now,
            reservationEndTime = now + ofHours(2),
            reservationStartTime = now + ofHours(1),
            maxAttendees = 10

        )
        val savedEvent = eventRepository.save(sampleEvent)

        // when
        eventRepository.deleteById(savedEvent.id!!)
        val deletedEvent = eventRepository.findByIdOrNull(savedEvent.id!!)

        // then
        assertThat(deletedEvent).isEqualTo(null)
    }
}
