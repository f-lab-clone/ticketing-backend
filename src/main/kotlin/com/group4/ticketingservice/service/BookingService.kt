package com.group4.ticketingservice.service

import com.group4.ticketingservice.entity.Booking
import com.group4.ticketingservice.repository.BookingRepository
import com.group4.ticketingservice.repository.PerformanceRepository
import com.group4.ticketingservice.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.OffsetDateTime

@Service
class BookingService @Autowired constructor(
    private val userRepository: UserRepository,
    private val performanceRepository: PerformanceRepository,
    private val bookingRepository: BookingRepository,
    private val clock: Clock
) {
    fun createBooking(userId: Long, performanceId: Long): Booking {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("User not found")
        }
        val performance = performanceRepository.findById(performanceId).orElseThrow {
            IllegalArgumentException("Performance not found")
        }
        val booking = Booking(user = user, performance = performance, bookedAt = OffsetDateTime.now(clock))

        return bookingRepository.save(booking)
    }

    fun getBooking(bookingId: Long): Booking {
        return bookingRepository.findById(bookingId).orElseThrow {
            IllegalArgumentException("Booking not found")
        }
    }

    fun updateBooking(bookingId: Long, performanceId: Long): Booking {
        val booking: Booking = bookingRepository.findById(bookingId).orElseThrow {
            IllegalArgumentException("Booking not found")
        }
        val performance = performanceRepository.findById(performanceId).orElseThrow {
            IllegalArgumentException("Performance not found")
        }
        booking.performance = performance

        return bookingRepository.save(booking)
    }

    fun deleteBooking(id: Long) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id)
        } else {
            throw IllegalArgumentException("Booking not found")
        }
    }
}
