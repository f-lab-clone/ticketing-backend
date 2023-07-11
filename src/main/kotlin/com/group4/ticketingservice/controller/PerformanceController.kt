package com.group4.ticketingservice.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.group4.ticketingservice.dto.PerformanceCreateRequest

@RestController
@RequestMapping("/performances")
class PerformanceController(private val performanceService: PerformanceService) {

    @PostMapping
    fun createPerformance(@RequestBody request: PerformanceCreateRequest): ResponseEntity<PerformanceResponse> {
        val performance = performanceService.createPerformance(
            request.name, request.date, request.bookingStartTime, request.bookingEndTime
        )
        val response = PerformanceResponse(
            id = performance.id!!,
            title = performance.title
            date = performance.date,
            bookingStartTime = performance.bookingStartTime,
            bookingEndTime = performance.bookingEndTime
        )
        return ResponseEntity.ok(response)
    }
}