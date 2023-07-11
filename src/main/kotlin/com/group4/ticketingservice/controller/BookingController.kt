package com.group4.ticketingservice.dto

@RestController
@RequestMapping("/bookings")
class BookingController(private val bookingService: BookingService) {
    @PostMapping
    fun createBooking(@RequestBody request: BookingCreateRequest): ResponseEntity<BookingResponse> {
        val booking = bookingService.createBooking(request.performanceId, request.userId, request.seatIds)
        val response = BookingResponse(
            id = booking.id!!,
            performanceId = booking.performanceId,
            userId = booking.userId,
            seatIds = booking.seatId
        )
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getBooking(@PathVariable id: Long): ResponseEntity<BookingResponse> {
    }

    @PutMapping("/{id}")
    fun updateBooking(
        @PathVariable id: Long,
        @RequestBody request: BookingUpdateRequest
    ): ResponseEntity<BookingResponse> {
        val booking = bookingService.updateBooking(id, request.performanceId)
        val response = BookingResponse(
            id = booking.id!!,
            user = booking.user,
            performance = booking.performance,
            bookedAt = booking.bookedAt
        )
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deleteBooking(@PathVariable id: Long): ResponseEntity<Void> {
        bookingService.deleteBooking(id)
        return ResponseEntity.noContent().build()
    }
}