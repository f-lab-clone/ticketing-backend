package com.group4.ticketingservice.service

import com.group4.ticketingservice.entity.Booking
import com.group4.ticketingservice.repository.BookingRepository
import com.group4.ticketingservice.repository.PerformanceRepository
import com.group4.ticketingservice.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookingService(
    private val userRepository: UserRepository,
    private val performanceRepository: PerformanceRepository,
    private val bookingRepository: BookingRepository
) {
    @Transactional
    fun createBooking(userId: Long, performanceId: Long): Booking {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("User not found")
        }
        val performance = performanceRepository.findById(performanceId).orElseThrow {
            IllegalArgumentException("Performance not found")
        }
        val booking = Booking(user = user, performance = performance)

        return bookingRepository.save(booking)
    }

    fun getBooking(bookingId: Long): Booking {
        return bookingRepository.findById(bookingId).orElseThrow {
            IllegalArgumentException("Booking not found")
        }
    }

    @Transactional
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

    @Transactional
    fun deleteBooking(id: Long) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id)
        } else {
            throw IllegalArgumentException("Booking not found")
        }
    }
}
