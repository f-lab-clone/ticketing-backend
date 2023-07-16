package com.group4.ticketingservice.controller

import com.group4.ticketingservice.dto.BookingCreateRequest
import com.group4.ticketingservice.dto.BookingResponse
import com.group4.ticketingservice.dto.BookingUpdateRequest
import com.group4.ticketingservice.entity.Booking
import com.group4.ticketingservice.service.BookingService
import io.mockk.InternalPlatformDsl.toStr
import kotlinx.datetime.LocalDateTime
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
@RequestMapping("/bookings")
class BookingController(val bookingService: BookingService) {
    @PostMapping
    fun createBooking(@RequestBody request: BookingCreateRequest): ResponseEntity<BookingResponse> {
        val booking: Booking = bookingService.createBooking(
            request.performanceId,
            request.userId
        )
        val response = BookingResponse(
            id = booking.id!!,
            performanceId = booking.performance.id!!,
            userId = booking.user.id!!,
            bookedAt = LocalDateTime.parse(booking.bookedAt.toStr())
        )
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getBooking(@PathVariable id: Long): ResponseEntity<BookingResponse> {
        val booking = bookingService.getBooking(id)
        val response = BookingResponse(
            id = booking.id!!,
            performanceId = booking.performance.id!!,
            userId = booking.user.id!!,
            bookedAt = LocalDateTime.parse(booking.bookedAt.toStr())
        )
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    fun updateBooking(
        @PathVariable id: Long,
        @RequestBody request: BookingUpdateRequest
    ): ResponseEntity<BookingResponse> {
        val booking = bookingService.updateBooking(id, request.performanceId)
        val response = BookingResponse(
            id = booking.id!!,
            performanceId = booking.performance.id!!,
            userId = booking.user.id!!,
            bookedAt = LocalDateTime.parse(booking.bookedAt.toStr())
        )
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deleteBooking(@PathVariable id: Long): ResponseEntity<Void> {
        bookingService.deleteBooking(id)
        return ResponseEntity.noContent().build()
    }
}
