package com.group4.ticketingservice.Reservation

import com.group4.ticketingservice.AbstractIntegrationTest
import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.entity.Reservation
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.EventRepository
import com.group4.ticketingservice.repository.ReservationRepository
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.utils.Authority
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.OffsetDateTime

class ReservationRepositoryTest @Autowired constructor(
    val userRepository: UserRepository,
    val eventRepository: EventRepository,
    val reservationRepository: ReservationRepository
) : AbstractIntegrationTest() {

    object testFields {
        const val testName = "minjun"
        const val testUserName = "minjun3021@qwer.com"
        const val password = "1234"
    }
    private val sampleUser = User(
        name = testFields.testName,
        email = testFields.testUserName,
        password = BCryptPasswordEncoder().encode(testFields.password),
        authority = Authority.USER
    )
    private val sampleEvent = Event(
        title = "test title",
        date = OffsetDateTime.now(),
        reservationEndTime = OffsetDateTime.now(),
        reservationStartTime = OffsetDateTime.now(),
        maxAttendees = 10

    )
    private val sampleReservation = Reservation(
        user = sampleUser,
        event = sampleEvent,
        bookedAt = OffsetDateTime.now()
    )

    @Test
    fun `ReservationRepository_save without mocked clock and OffsetDateTime should return savedReservation`() {
        // given
        val sampleEvent = Event(
            title = "test title 2",
            date = OffsetDateTime.now(),
            reservationEndTime = OffsetDateTime.now(),
            reservationStartTime = OffsetDateTime.now(),
            maxAttendees = 10

        )
        val sampleReservation = Reservation(
            user = sampleUser,
            event = sampleEvent,
            bookedAt = OffsetDateTime.now()
        )
        userRepository.save(sampleUser)
        eventRepository.save(sampleEvent)

        // when
        val savedReservation = reservationRepository.save(sampleReservation)

        // then
        assertThat(savedReservation).isEqualTo(sampleReservation)
    }

    @Test
    fun `ReservationRepository_save with mocked UTC clock and OffsetDateTime should return savedReservation`() {
        // given
        userRepository.save(sampleUser)
        eventRepository.save(sampleEvent)

        // when
        val savedReservation = reservationRepository.save(sampleReservation)

        // then
        assertThat(savedReservation).isEqualTo(sampleReservation)
    }
}
