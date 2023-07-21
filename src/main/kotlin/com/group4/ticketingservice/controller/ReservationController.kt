package com.group4.ticketingservice.controller

import com.group4.ticketingservice.entity.Reservation
import com.group4.ticketingservice.repository.ReservationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ReservationController(@Autowired private val ReservationRepository: ReservationRepository) {

    @GetMapping("/reservation")
    fun getReservations(): List<Reservation> {
        return ReservationRepository.findAll()
    }

    @PostMapping("/reservation")
    fun reservePerformance(@RequestBody reservation: Reservation): Reservation {
        return ReservationRepository.save(reservation)
    }
}
