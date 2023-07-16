package com.group4.ticketingservice.service

import com.group4.ticketingservice.entity.Performance
import com.group4.ticketingservice.repository.PerformanceRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.LocalDateTime

class PerformanceServiceTest {
    private val performanceRepository: PerformanceRepository = mockk()
    private val performanceService: PerformanceService = PerformanceService(
        performanceRepository = performanceRepository
    )
    private val samplePerformance: Performance = Performance(
        id = 1,
        title = "test title",
        date = LocalDateTime.now(),
        bookingEndTime = LocalDateTime.now() + Duration.ofHours(2),
        bookingStartTime = LocalDateTime.now() + Duration.ofHours(1),
        maxAttendees = 10
    )

    @Test
    fun createPerformance() {
        every { performanceRepository.save(any()) } returns samplePerformance
        performanceService.createPerformance(
            title = samplePerformance.title,
            date = samplePerformance.date,
            bookingStartTime = samplePerformance.bookingStartTime,
            bookingEndTime = samplePerformance.bookingEndTime,
            maxAttendees = samplePerformance.maxAttendees
        )
        verify(exactly = 1) { performanceRepository.save(any()) }
    }
}
