package com.group4.ticketingservice.controller

import com.group4.ticketingservice.dto.PerformanceCreateRequest
import com.group4.ticketingservice.dto.PerformanceResponse
import com.group4.ticketingservice.service.PerformanceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/performances")
class PerformanceController @Autowired constructor(
    val performanceService: PerformanceService
) {

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
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }

    @GetMapping("/{id}")
    fun getPerformance(@PathVariable id: Long): ResponseEntity<PerformanceResponse> {
        val performance = performanceService.getPerformance(id)
        val response = PerformanceResponse(
            id = performance.id!!,
            title = performance.title,
            date = performance.date,
            bookingStartTime = performance.bookingStartTime,
            bookingEndTime = performance.bookingEndTime,
            maxAttendees = performance.maxAttendees
        )
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }

    @PutMapping("/{id}")
    fun updatePerformance(
        @PathVariable id: Long,
        @RequestBody request: PerformanceCreateRequest
    ): ResponseEntity<PerformanceResponse> {
        val performance = performanceService.updatePerformance(
            id,
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
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }

    @DeleteMapping("/{id}")
    fun deletePerformance(@PathVariable id: Long): ResponseEntity<Unit> {
        performanceService.deletePerformance(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}
