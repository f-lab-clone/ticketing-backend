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
}
