package com.group4.ticketingservice.service

import com.group4.ticketingservice.entity.Performance
import com.group4.ticketingservice.repository.PerformanceRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PerformanceService(
    val performanceRepository: PerformanceRepository
) {
    fun createPerformance(
        title: String,
        date: LocalDateTime,
        bookingStartTime: LocalDateTime,
        bookingEndTime: LocalDateTime,
        maxAttendees: Int
    ): Performance {
        val performance = Performance(
            title = title,
            date = date,
            bookingStartTime = bookingStartTime,
            bookingEndTime = bookingEndTime,
            maxAttendees = maxAttendees
        )
        return performanceRepository.save(performance)
    }

    fun getPerformance(id: Long): Performance? {
        return performanceRepository.findById(id).orElse(null)
    }

    fun getPerformances(): List<Performance> {
        return performanceRepository.findAll()
    }

    fun updatePerformance(
        id: Long,
        title: String,
        date: LocalDateTime,
        bookingStartTime: LocalDateTime,
        bookingEndTime: LocalDateTime,
        maxAttendees: Int
    ): Performance {
        val performance = performanceRepository.findById(id).orElseThrow {
            IllegalArgumentException("Performance not found")
        }
        performance.title = title
        performance.date = date
        performance.bookingStartTime = bookingStartTime
        performance.bookingEndTime = bookingEndTime
        performance.maxAttendees = maxAttendees

        return performanceRepository.save(performance)
    }

    fun deletePerformance(id: Long) {
        if (performanceRepository.existsById(id)) {
            performanceRepository.deleteById(id)
        } else {
            throw IllegalArgumentException("Performance not found")
        }
    }
}
