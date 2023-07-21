package com.group4.ticketingservice.controller

import com.group4.ticketingservice.dto.ReservationFromdto
import com.group4.ticketingservice.entity.Reservation
import com.group4.ticketingservice.service.ReservationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ReservationController(@Autowired private val reservationService: ReservationService) {

    @GetMapping("/reservation")
    fun getReservations(): List<Reservation> {
        return reservationService.getList()
    }

    @PostMapping("/reservation")
    fun reservePerformance(@RequestBody reservation: ReservationFromdto): Int? {
        return reservationService.create(reservation)
    }
}
