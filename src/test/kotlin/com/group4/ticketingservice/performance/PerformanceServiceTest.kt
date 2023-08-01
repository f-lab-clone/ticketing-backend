package com.group4.ticketingservice.performance

import com.group4.ticketingservice.entity.Performance
import com.group4.ticketingservice.repository.PerformanceRepository
import com.group4.ticketingservice.service.PerformanceService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

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
    fun `PerformanceService_createPerformance invoke PerformanceRepository_findById`() {
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

    @Test
    fun `PerformanceService_getPerformance invoke PerformanceRepository_findById`() {
        every { performanceRepository.findById(any()) } returns Optional.of(samplePerformance)
        performanceService.getPerformance(samplePerformance.id!!)
        verify(exactly = 1) { performanceRepository.findById(any()) }
    }

    @Test
    fun `PerformanceService_getPerformances invoke PerformanceRepository_findAll`() {
        every { performanceRepository.findAll() } returns listOf(samplePerformance)
        performanceService.getPerformances()
        verify(exactly = 1) { performanceRepository.findAll() }
    }

    @Test
    fun `PerformanceService_updatePerformance invoke PerformanceRepository_findById`() {
        val updatedPerformance = Performance(
            id = samplePerformance.id!!,
            title = "updated title",
            date = samplePerformance.date,
            bookingEndTime = samplePerformance.bookingEndTime,
            bookingStartTime = samplePerformance.bookingStartTime,
            maxAttendees = samplePerformance.maxAttendees
        )
        every { performanceRepository.findById(any()) } returns Optional.of(samplePerformance)
        every { performanceRepository.save(any()) } returns updatedPerformance

        val result: Performance = performanceService.updatePerformance(
            id = samplePerformance.id!!,
            title = updatedPerformance.title,
            date = updatedPerformance.date,
            bookingStartTime = updatedPerformance.bookingStartTime,
            bookingEndTime = updatedPerformance.bookingEndTime,
            maxAttendees = updatedPerformance.maxAttendees
        )
        assert(result == updatedPerformance)
        verify(exactly = 1) { performanceRepository.findById(any()) }
    }

    @Test
    fun `PerformanceService_deletePerformance invoke PerformanceRepository_findById`() {
        every { performanceRepository.existsById(any()) } returns true
        every { performanceRepository.deleteById(any()) } returns Unit
        performanceService.deletePerformance(samplePerformance.id!!)
        verify(exactly = 1) { performanceRepository.existsById(any()) }
        verify(exactly = 1) { performanceRepository.deleteById(any()) }
    }
}
