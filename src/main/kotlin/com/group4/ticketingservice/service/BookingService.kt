package com.group4.ticketingservice.service


@Service
class BookingService(
    private val userRepository: UserRepository,
    private val performanceRepository: PerformanceRepository,
    private val bookingRepository: BookingRepository
) {
    @Transactional
    fun createBooking(userId: Long, performanceId: Long): Booking {
        val user = userRepository.findById(userId).orElseThrow 
        { IllegalArgumentException("User not found") }
        val performance = performanceRepository.findById(performanceId).orElseThrow 
        { IllegalArgumentException("Performance not found") }
        val booking = Booking(user=user, performance=performance)

        return bookingRepository.save(booking)
    }

    fun getBooking(id: Long): Booking {
        return bookingRepository.findById(id).orElseThrow 
        { IllegalArgumentException("Booking not found") }
    }

    @Transactional
    fun updateBooking(id: Long, userId: Long, performanceId: Long): Booking {
        val user = userRepository.findById(userId).orElseThrow 
        { IllegalArgumentException("User not found") }
        val performance = performanceRepository.findById(performanceId).orElseThrow 
        { IllegalArgumentException("Performance not found") }
        val booking = bookingRepository.findById(id).orElseThrow 
        { IllegalArgumentException("Booking not found") }
        booking.user = user
        booking.performance = performance

        return bookingRepository.save(booking)
    }

    @Transactional
    fun deleteBooking(id: Long) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id)
        } else {
            throw IllegalArgumentException("Booking not found")
        }
    }
}