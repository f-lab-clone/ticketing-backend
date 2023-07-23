package com.group4.ticketingservice.util

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

@SpringBootTest
class ClockFactoryTest(
    @Autowired val clockFactory: ClockFactory
) {
    private val SAMPLE_CLOCK = Clock.fixed(Instant.parse("3332-08-22T10:00:00Z"), ZoneOffset.UTC)

    @MockkBean
    private lateinit var clock: Clock

    @Test
    fun `now should return current time`() {
        every { clock.instant() } returns SAMPLE_CLOCK.instant()

        assert(clockFactory.now().year == 3332)
    }


}
