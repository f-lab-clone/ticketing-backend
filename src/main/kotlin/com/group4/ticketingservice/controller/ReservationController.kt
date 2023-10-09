package com.group4.ticketingservice.controller

import com.group4.ticketingservice.dto.ReservationCreateRequest
import com.group4.ticketingservice.dto.ReservationResponse
import com.group4.ticketingservice.dto.ReservationUpdateRequest
import com.group4.ticketingservice.entity.Reservation
import com.group4.ticketingservice.service.ReservationService
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reservations")
class ReservationController(val reservationService: ReservationService) {
    @PostMapping
    fun createReservation(
        @AuthenticationPrincipal userId: Int,
        @RequestBody @Valid
        request: ReservationCreateRequest
    ): ResponseEntity<ReservationResponse> {
        val reservation: Reservation = reservationService.createReservation(
            request.eventId!!,
            userId,
            request.name!!,
            request.phoneNumber!!,
            request.postCode!!,
            request.address!!
        )
        val response = ReservationResponse(
            id = reservation.id!!,
            eventId = reservation.event.id!!,
            userId = reservation.user.id!!,
            createdAt = reservation.createdAt,
            name = reservation.name,
            phoneNumber = reservation.phoneNumber,
            address = reservation.address,
            postCode = reservation.postCode
        )

        val headers = HttpHeaders()
        headers.set("Content-Location", "/reservations/%d".format(reservation.id!!))

        return ResponseEntity(response, headers, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getReservation(
        request: HttpServletRequest,
        @PathVariable id: Int
    ): ResponseEntity<ReservationResponse> {
        val reservation = reservationService.getReservation(id)

        val response = ReservationResponse(
            id = reservation.id!!,
            eventId = reservation.event.id!!,
            userId = reservation.user.id!!,
            createdAt = reservation.createdAt,
            name = reservation.name,
            phoneNumber = reservation.phoneNumber,
            address = reservation.address,
            postCode = reservation.postCode
        )

        val headers = HttpHeaders()
        headers.set("Content-Location", request.requestURI)

        return ResponseEntity(response, headers, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updateReservation(
        request: HttpServletRequest,
        @PathVariable id: Int,
        @RequestBody @Valid
        reservationRequest: ReservationUpdateRequest
    ): ResponseEntity<ReservationResponse> {
        val reservation = reservationService.updateReservation(id, reservationRequest.eventId!!)

        val response = ReservationResponse(
            id = reservation.id!!,
            eventId = reservation.event.id!!,
            userId = reservation.user.id!!,
            createdAt = reservation.createdAt,
            name = reservation.name,
            phoneNumber = reservation.phoneNumber,
            address = reservation.address,
            postCode = reservation.postCode
        )

        val headers = HttpHeaders()
        headers.set("Content-Location", request.requestURI)

        return ResponseEntity(response, headers, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteReservation(
        @AuthenticationPrincipal userId: Int,
        @PathVariable id: Int
    ): ResponseEntity<Any> {
        reservationService.deleteReservation(userId, id)
        return ResponseEntity(null, HttpStatus.OK)
    }

    @GetMapping
    fun getReservations(
        request: HttpServletRequest,
        @AuthenticationPrincipal userId: Int,
        @PageableDefault(size = 10) pageable: Pageable
    ): ResponseEntity<Page<Reservation>> {
        val page = reservationService.getReservations(userId, pageable)

        val headers = HttpHeaders()
        headers.set("Content-Location", request.requestURI)

        return ResponseEntity(page, headers, HttpStatus.OK)
    }
}
