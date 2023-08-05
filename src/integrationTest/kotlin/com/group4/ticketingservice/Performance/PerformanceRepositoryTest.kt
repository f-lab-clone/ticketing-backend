package com.group4.ticketingservice.Performance

import com.group4.ticketingservice.AbstractIntegrationTest
import com.group4.ticketingservice.entity.Performance
import com.group4.ticketingservice.repository.PerformanceRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import java.time.Duration.ofHours
import java.time.OffsetDateTime
import java.time.ZoneOffset

class PerformanceRepositoryTest(
    @Autowired val performanceRepository: PerformanceRepository
) : AbstractIntegrationTest() {
    @Test
    fun `PerformanceRepository_save should return savedPerformance`() {
        // given
        val now = OffsetDateTime.now(ZoneOffset.UTC)
        val samplePerformance = Performance(
            title = "test title",
            date = now,
            bookingEndTime = now + ofHours(2),
            bookingStartTime = now + ofHours(1),
            maxAttendees = 10
        )

        // when
        val savedPerformance = performanceRepository.save(samplePerformance)

        // then
        assertThat(savedPerformance).isEqualTo(samplePerformance)
    }

    @Test
    fun `PerformanceRepository_findByIdOrNull should return performance`() {
        // given
        val now = OffsetDateTime.now(ZoneOffset.UTC)
        val samplePerformance = Performance(
            title = "test title",
            date = now,
            bookingEndTime = now + ofHours(2),
            bookingStartTime = now + ofHours(1),
            maxAttendees = 10
        )
        val savedPerformance = performanceRepository.save(samplePerformance)

        // when
        val foundPerformance: Performance? = performanceRepository.findByIdOrNull(
            savedPerformance.id!!
        )

        // then
        assert(foundPerformance?.id == samplePerformance.id)
    }

    @Test
    fun `PerformanceRepository_findAll should return list of performances`() {
        // given
        val now = OffsetDateTime.now(ZoneOffset.UTC)
        val samplePerformance = Performance(
            title = "test title",
            date = now,
            bookingEndTime = now + ofHours(2),
            bookingStartTime = now + ofHours(1),
            maxAttendees = 10
        )
        performanceRepository.save(samplePerformance)

        // when
        val performances = performanceRepository.findAll()

        // then
        assertInstanceOf(List::class.java, performances)
        assert(performances.size > 0)
    }

    @Test
    fun `PerformanceRepository_delete should delete performance`() {
        // given
        val now = OffsetDateTime.now(ZoneOffset.UTC)
        val samplePerformance = Performance(
            title = "test title",
            date = now,
            bookingEndTime = now + ofHours(2),
            bookingStartTime = now + ofHours(1),
            maxAttendees = 10
        )
        val savedPerformance = performanceRepository.save(samplePerformance)

        // when
        performanceRepository.deleteById(savedPerformance.id!!)
        val deletedPerformance = performanceRepository.findByIdOrNull(savedPerformance.id!!)

        // then
        assertThat(deletedPerformance).isEqualTo(null)
    }
}
