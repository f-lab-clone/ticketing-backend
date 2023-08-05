package com.group4.ticketingservice.booking

import com.group4.ticketingservice.AbstractIntegrationTest
import com.group4.ticketingservice.config.ClockConfig
import com.group4.ticketingservice.entity.Booking
import com.group4.ticketingservice.entity.Performance
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.BookingRepository
import com.group4.ticketingservice.repository.PerformanceRepository
import com.group4.ticketingservice.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import java.time.Clock
import java.time.OffsetDateTime

@Import(ClockConfig::class)
class BookingRepositoryTest @Autowired constructor(
    val userRepository: UserRepository,
    val performanceRepository: PerformanceRepository,
    val bookingRepository: BookingRepository,
    clock: Clock
) : AbstractIntegrationTest() {

    private val sampleUser = User(
        name = "John Doe",
        email = "john@email.test"
    )
    private val samplePerformance = Performance(
        title = "test title",
        date = OffsetDateTime.now(clock),
        bookingEndTime = OffsetDateTime.now(clock),
        bookingStartTime = OffsetDateTime.now(clock),
        maxAttendees = 10
    )
    private val sampleBooking = Booking(
        user = sampleUser,
        performance = samplePerformance,
        bookedAt = OffsetDateTime.now(clock)
    )

    @Test
    fun `BookingRepository_save without mocked clock and OffsetDateTime should return savedBooking`() {
        // given
        val sampleUser = User(
            name = "James",
            email = "james@email.test"
        )
        val samplePerformance = Performance(
            title = "test title 2",
            date = OffsetDateTime.now(),
            bookingEndTime = OffsetDateTime.now(),
            bookingStartTime = OffsetDateTime.now(),
            maxAttendees = 10
        )
        val sampleBooking = Booking(
            user = sampleUser,
            performance = samplePerformance,
            bookedAt = OffsetDateTime.now()
        )
        userRepository.save(sampleUser)
        performanceRepository.save(samplePerformance)

        // when
        val savedBooking = bookingRepository.save(sampleBooking)

        // then
        assertThat(savedBooking).isEqualTo(sampleBooking)
    }

    @Test
    fun `BookingRepository_save with mocked UTC clock and OffsetDateTime should return savedBooking`() {
        // given
        userRepository.save(sampleUser)
        performanceRepository.save(samplePerformance)

        // when
        val savedBooking = bookingRepository.save(sampleBooking)

        // then
        assertThat(savedBooking).isEqualTo(sampleBooking)
    }
}
