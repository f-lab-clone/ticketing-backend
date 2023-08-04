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
import java.time.Duration.ofHours
import java.time.OffsetDateTime

// @ExtendWith(SpringExtension::class)
@Import(ClockConfig::class)
class BookingRepositoryTest @Autowired constructor(
    val userRepository: UserRepository,
    val performanceRepository: PerformanceRepository,
    val bookingRepository: BookingRepository,
    val clock: Clock
) : AbstractIntegrationTest() {

    @Test
    fun `BookingRepository_save should return savedBooking`() {
        // given
        val sampleUser = User(
            name = "John Doe",
            email = "john@email.test"
        )
        val samplePerformance = Performance(
            title = "test title",
            date = OffsetDateTime.now(clock),
            bookingEndTime = OffsetDateTime.now(clock) + ofHours(2),
            bookingStartTime = OffsetDateTime.now(clock) + ofHours(1),
            maxAttendees = 10
        )
        val sampleBooking = Booking(
            user = sampleUser,
            performance = samplePerformance,
            bookedAt = OffsetDateTime.now(clock)
        )
        userRepository.save(sampleUser)
        performanceRepository.save(samplePerformance)

        // when
        val savedBooking = bookingRepository.save(sampleBooking)

        // then
        assertThat(savedBooking).isEqualTo(sampleBooking)
    }
}
