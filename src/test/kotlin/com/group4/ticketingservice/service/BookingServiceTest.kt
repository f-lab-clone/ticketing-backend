package com.group4.ticketingservice.service

import com.group4.ticketingservice.entity.Booking
import com.group4.ticketingservice.entity.Performance
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.BookingRepository
import com.group4.ticketingservice.repository.PerformanceRepository
import com.group4.ticketingservice.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Duration.ofHours
import java.time.LocalDateTime
import java.util.*

class BookingServiceTest {
    private val userRepository: UserRepository = mockk()
    private val performanceRepository: PerformanceRepository = mockk()
    private val bookingRepository: BookingRepository = mockk()
    private val bookingService: BookingService = BookingService(
        userRepository = userRepository,
        performanceRepository = performanceRepository,
        bookingRepository = bookingRepository
    )
    private val sampleUser: User = User(id = 1, name = "John Doe", email = "john@email.com")
    private val samplePerformance: Performance = Performance(
        id = 1,
        title = "test title",
        date = LocalDateTime.now(),
        bookingEndTime = LocalDateTime.now() + ofHours(2),
        bookingStartTime = LocalDateTime.now() + ofHours(1),
        maxAttendees = 10
    )

    private val sampleBooking: Booking = Booking(user = sampleUser, performance = samplePerformance)

    @Test
    fun `BookingService_createBooking invoke BookingRepository_save`() {
        every { userRepository.findById(any()) } returns Optional.of(sampleUser)
        every { performanceRepository.findById(any()) } returns Optional.of(samplePerformance)
        every { bookingRepository.save(any()) } returns sampleBooking
        bookingService.createBooking(1, 1)
        verify(exactly = 1) { bookingRepository.save(any()) }
    }

    @Test
    fun `BookingService_getBooking invoke BookingRepository_findById`() {
        every { bookingRepository.findById(any()) } returns Optional.of(sampleBooking)
        bookingService.getBooking(1)
        verify(exactly = 1) { bookingRepository.findById(any()) }
    }

    @Test
    fun `BookingService_updateBooking invoke BookingRepository_save`() {
        every { bookingRepository.findById(any()) } returns Optional.of(sampleBooking)
        every { performanceRepository.findById(any()) } returns Optional.of(samplePerformance)
        every { bookingRepository.save(any()) } returns sampleBooking
        bookingService.updateBooking(1, 1)
        verify(exactly = 1) { bookingRepository.save(any()) }
    }

    @Test
    fun `BookingService_deleteBooking invoke BookingRepository_deleteById`() {
        every { bookingRepository.existsById(any()) } returns true
        every { bookingRepository.deleteById(any()) } returns Unit
        bookingService.deleteBooking(1)
        verify(exactly = 1) { bookingRepository.deleteById(any()) }
    }

    @Test
    fun `BookingService_deleteBooking throw IllegalArgumentException`() {
        every { bookingRepository.existsById(any()) } returns false
        val exception = assertThrows<IllegalArgumentException> { bookingService.deleteBooking(1) }
        assert(exception.message == "Booking not found")
        verify(exactly = 0) { bookingRepository.deleteById(any()) }
    }
}
