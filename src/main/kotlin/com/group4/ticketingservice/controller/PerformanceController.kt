package com.group4.ticketingservice.controller

import com.group4.ticketingservice.dto.PerformanceCreateRequest
import com.group4.ticketingservice.dto.PerformanceResponse
import com.group4.ticketingservice.service.PerformanceService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping

@RestController
@RequestMapping("/performances")
class PerformanceController(val performanceService: PerformanceService) {

    @PostMapping
    fun createPerformance(@RequestBody request: PerformanceCreateRequest): ResponseEntity<PerformanceResponse> {
        val performance = performanceService.createPerformance(
            request.title,
            request.date,
            request.bookingStartTime,
            request.bookingEndTime,
            request.maxAttendees
        )
        val response = PerformanceResponse(
            id = performance.id!!,
            title = performance.title,
            date = performance.date,
            bookingStartTime = performance.bookingStartTime,
            bookingEndTime = performance.bookingEndTime,
            maxAttendees = performance.maxAttendees
        )
        return ResponseEntity.ok(response)
    }
}
