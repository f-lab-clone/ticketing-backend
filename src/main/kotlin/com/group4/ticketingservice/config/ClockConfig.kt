package com.group4.ticketingservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

@Configuration
class ClockConfig {
    @Value("\${time-travel.instant:null}")
    private val timeTravelInstant: String? = null

    @Value("\${time-travel.zone:null}")
    private val timeTravelZone: String? = null

    @Bean
    @ConditionalOnProperty(value = ["time-travel.enabled"], havingValue = "false", matchIfMissing = true)
    fun defaultClock(): Clock {
        return Clock.systemDefaultZone()
    }

    @Bean
    @ConditionalOnProperty(value = ["time-travel.enabled"], havingValue = "true")
    fun clock(): Clock {
        return Clock.fixed(Instant.parse(timeTravelInstant), ZoneId.of(timeTravelZone))
    }
}
