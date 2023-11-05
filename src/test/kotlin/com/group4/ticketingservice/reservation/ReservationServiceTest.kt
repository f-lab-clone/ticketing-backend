package com.group4.ticketingservice.reservation

import com.group4.ticketingservice.dto.QueueResponseDTO
import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.entity.Reservation
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.EventRepository
import com.group4.ticketingservice.repository.ReservationRepository
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.service.ReservationService
import com.group4.ticketingservice.utils.exception.CustomException
import com.group4.ticketingservice.utils.exception.ErrorCodes
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.time.Duration.ofHours
import java.time.OffsetDateTime
import java.util.Optional

class ReservationServiceTest() {
    private val userRepository: UserRepository = mockk()
    private val eventRepository: EventRepository = mockk()
    private val reservationRepository: ReservationRepository = mockk()
    private val restTemplate: RestTemplate = mockk()

    private val reservationService: ReservationService = ReservationService(
        userRepository = userRepository,
        eventRepository = eventRepository,
        reservationRepository = reservationRepository,
        queueServerURL = "url",
        restTemplate = restTemplate
    )
    val sampleUserId = 1

    val sampleUser = User(
        name = "minjun3021@qwer.com",
        email = "minjun",
        password = "1234",

        id = sampleUserId,
        phone = "010-1234-5678"
    )

    private val sampleEvent: Event = Event(
        id = 1,
        name = "test title",
        startDate = OffsetDateTime.now(),
        endDate = OffsetDateTime.now(),
        reservationEndTime = OffsetDateTime.now() + ofHours(2),
        reservationStartTime = OffsetDateTime.now() - ofHours(2),
        maxAttendees = 10
    )
    private val sampleWrongEvent: Event = Event(
        id = 1,
        name = "test title",
        startDate = OffsetDateTime.now(),
        endDate = OffsetDateTime.now(),
        reservationEndTime = OffsetDateTime.now(),
        reservationStartTime = OffsetDateTime.now(),
        maxAttendees = 10
    )

    private val sampleReservation: Reservation = Reservation(
        user = sampleUser,
        event = sampleEvent
    )
    private val queueSuccess: QueueResponseDTO = QueueResponseDTO(
        status = true,
        message = "",
        data = null
    )

    @Test
    fun `ReservationService_createReservation invoke ReservationRepository_save`() {
        every { userRepository.getReferenceById(any()) } returns sampleUser
        every { eventRepository.findByIdWithPesimisticLock(any()) } returns sampleEvent
        every { eventRepository.findByIdWithOptimisicLock(any()) } returns sampleEvent
        every { restTemplate.exchange(any() as String, HttpMethod.DELETE, null, QueueResponseDTO::class.java) } returns ResponseEntity.ok(queueSuccess)
        every { eventRepository.saveAndFlush(any()) } returns sampleEvent
        every { reservationRepository.saveAndFlush(any()) } returns sampleReservation
        reservationService.createReservation(1, 1, "김해군", "010-1234-5678", 1, "서울")
        verify(exactly = 1) { reservationRepository.saveAndFlush(any()) }
    }

    @Test
    fun `ReservationService_createReservation throw custom exception when time not allowed`() {
        every { userRepository.getReferenceById(any()) } returns sampleUser
        every { eventRepository.findByIdWithPesimisticLock(any()) } returns sampleWrongEvent
        every { restTemplate.exchange(any() as String, HttpMethod.DELETE, null, QueueResponseDTO::class.java) } returns ResponseEntity.ok(queueSuccess)
        every { eventRepository.saveAndFlush(any()) } returns sampleEvent
        every { reservationRepository.saveAndFlush(any()) } returns sampleReservation

        assertThrows<CustomException> { reservationService.createReservation(1, 1, "김해군", "010-1234-5678", 1, "서울") }
    }

    @Test
    fun `ReservationService_createReservation throw custom exception when doesn't have waiting ticket`() {
        every { userRepository.getReferenceById(any()) } returns sampleUser
        every { eventRepository.findByIdWithPesimisticLock(any()) } returns sampleWrongEvent
        every { restTemplate.exchange(any() as String, HttpMethod.DELETE, null, QueueResponseDTO::class.java) } throws HttpClientErrorException(HttpStatus.NOT_FOUND)
        every { eventRepository.saveAndFlush(any()) } returns sampleEvent
        every { reservationRepository.saveAndFlush(any()) } returns sampleReservation

        assertThrows<CustomException> { reservationService.createReservation(1, 1, "김해군", "010-1234-5678", 1, "서울") }
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
        every { reservationRepository.findById(any()) } returns Optional.of(sampleReservation)
        every { reservationRepository.delete(any()) } returns Unit
        reservationService.deleteReservation(sampleUserId, 1)
        verify(exactly = 1) { reservationRepository.delete(any()) }
    }

    @Test
    fun `ReservationService_deleteReservation throw CustomException`() {
        every { reservationRepository.findById(any()) } returns Optional.ofNullable(null)
        every { reservationRepository.delete(any()) } returns Unit

        val exception = assertThrows<CustomException> { reservationService.deleteReservation(sampleUserId, 1) }
        assert(exception.errorCode == ErrorCodes.ENTITY_NOT_FOUND)
        verify(exactly = 0) { reservationRepository.delete(any()) }
    }

    @Test
    fun `ReservationService_deleteReservation throw CustomException when deleting other's reservation`() {
        every { reservationRepository.findById(any()) } returns Optional.of(sampleReservation)
        every { reservationRepository.delete(any()) } returns Unit

        val exception = assertThrows<CustomException> { reservationService.deleteReservation(2, 1) }
        assert(exception.errorCode == ErrorCodes.FORBIDDEN)
    }
}
