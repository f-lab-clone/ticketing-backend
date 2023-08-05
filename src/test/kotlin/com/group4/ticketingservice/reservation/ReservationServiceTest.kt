package com.group4.ticketingservice.reservation

import com.group4.ticketingservice.config.ClockConfig
import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.entity.Reservation
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.EventRepository
import com.group4.ticketingservice.repository.ReservationRepository
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.service.ReservationService
import com.group4.ticketingservice.utils.Authority
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.support.AnnotationConfigContextLoader
import java.time.Clock
import java.time.Duration.ofHours
import java.time.OffsetDateTime
import java.util.*

@ContextConfiguration(
    classes = [ClockConfig::class],
    loader = AnnotationConfigContextLoader::class
)
@SpringBootTest
class ReservationServiceTest(
    @Autowired private val clock: Clock
) {
    private val userRepository: UserRepository = mockk()
    private val eventRepository: EventRepository = mockk()
    private val reservationRepository: ReservationRepository = mockk()
    private val reservationService: ReservationService = ReservationService(
        userRepository = userRepository,
        eventRepository = eventRepository,
        reservationRepository = reservationRepository,
        clock = clock
    )
    val sampleUser = User(
        name = "minjun3021@qwer.com",
        email = "minjun",
        password = "1234",
        authority = Authority.USER
    )
    private val sampleEvent: Event = Event(
        id = 1,
        title = "test title",
        date = OffsetDateTime.now(clock),
        reservationEndTime = OffsetDateTime.now(clock) + ofHours(2),
        reservationStartTime = OffsetDateTime.now(clock) + ofHours(1),
        maxAttendees = 10
    )

    private val sampleReservation: Reservation = Reservation(
        user = sampleUser,
        event = sampleEvent,
        bookedAt = OffsetDateTime.now(clock)
    )

    @Test
    fun `ReservationService_createReservation invoke ReservationRepository_save`() {
        every { userRepository.findById(any()) } returns Optional.of(sampleUser)
        every { eventRepository.findById(any()) } returns Optional.of(sampleEvent)
        every { reservationRepository.save(any()) } returns sampleReservation
        reservationService.createReservation(1, 1)
        verify(exactly = 1) { reservationRepository.save(any()) }
    }

    @Test
    fun `ReservationService_getReservation invoke ReservationRepository_findById`() {
        every { reservationRepository.findById(any()) } returns Optional.of(sampleReservation)
        reservationService.getReservation(1)
        verify(exactly = 1) { reservationRepository.findById(any()) }
    }

    @Test
    fun `ReservationService_updateReservation invoke ReservationRepository_save`() {
        every { reservationRepository.findById(any()) } returns Optional.of(sampleReservation)
        every { eventRepository.findById(any()) } returns Optional.of(sampleEvent)
        every { reservationRepository.save(any()) } returns sampleReservation
        reservationService.updateReservation(1, 1)
        verify(exactly = 1) { reservationRepository.save(any()) }
    }

    @Test
    fun `ReservationService_deleteReservation invoke ReservationRepository_deleteById`() {
        every { reservationRepository.existsById(any()) } returns true
        every { reservationRepository.deleteById(any()) } returns Unit
        reservationService.deleteReservation(1)
        verify(exactly = 1) { reservationRepository.deleteById(any()) }
    }

    @Test
    fun `ReservationService_deleteReservation throw IllegalArgumentException`() {
        every { reservationRepository.existsById(any()) } returns false
        val exception = assertThrows<IllegalArgumentException> { reservationService.deleteReservation(1) }
        assert(exception.message == "Reservation not found")
        verify(exactly = 0) { reservationRepository.deleteById(any()) }
    }
}
