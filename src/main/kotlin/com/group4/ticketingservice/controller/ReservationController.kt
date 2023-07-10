package com.group4.ticketingservice.controller

import com.group4.ticketingservice.domain.Reservation
import com.group4.ticketingservice.repository.ReservationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class ReservationController(@Autowired private val ReservationRepository: ReservationRepository) {

    @GetMapping("/read")
    fun getUsers(): List<Reservation> {
        return ReservationRepository.findAll()
    }

    @PostMapping("/write")
    fun createUser(@RequestBody user: Reservation): Reservation {
        return ReservationRepository.save(user)
    }
}