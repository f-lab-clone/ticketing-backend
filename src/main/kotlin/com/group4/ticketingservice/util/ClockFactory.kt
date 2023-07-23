package com.group4.ticketingservice.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Clock
import java.time.LocalDateTime

@Component
class ClockFactory {
    @Autowired
    private lateinit var clock: Clock

    fun now(): LocalDateTime {
        return LocalDateTime.now(clock)
    }
}