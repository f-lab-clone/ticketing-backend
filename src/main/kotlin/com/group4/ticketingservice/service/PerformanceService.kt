package com.group4.ticketingservice.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PerformanceService(private val performanceRepository: performanceRepository):
    @Transactional
    fun createPerformance(name: String, date: LocalDateTime, bookingStartTime: LocalDateTime, bookingEndTime: LocalDateTime): Performance {
        val performance = Performance(
            name=name, 
            date=date,
            bookingStartTime=bookingStartTime,
            bookingEndTime=bookingEndTime
        ) 
        return performanceRepository.save(performance)
    }