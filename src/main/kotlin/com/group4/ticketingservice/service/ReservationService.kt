package com.group4.ticketingservice.service

import com.group4.ticketingservice.entity.Reservation
import com.group4.ticketingservice.repository.EventRepository
import com.group4.ticketingservice.repository.ReservationRepository
import com.group4.ticketingservice.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.OffsetDateTime

@Service
class ReservationService @Autowired constructor(
    private val userRepository: UserRepository,
    private val eventRepository: EventRepository,
    private val reservationRepository: ReservationRepository,
    private val clock: Clock
) {
    fun createReservation(userId: Long, eventId: Long): Reservation {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("User not found")
        }
        val event = eventRepository.findById(eventId).orElseThrow {
            IllegalArgumentException("Event not found")
        }
        val reservation = Reservation(user = user, event = event, bookedAt = OffsetDateTime.now(clock))

        return reservationRepository.save(reservation)
    }

    fun getReservation(reservationId: Long): Reservation {
        return reservationRepository.findById(reservationId).orElseThrow {
            IllegalArgumentException("Reservation not found")
        }
    }

    fun updateReservation(reservationId: Long, eventId: Long): Reservation {
        val reservation: Reservation = reservationRepository.findById(reservationId).orElseThrow {
            IllegalArgumentException("Reservation not found")
        }
        val event = eventRepository.findById(eventId).orElseThrow {
            IllegalArgumentException("Event not found")
        }
        reservation.event = event

        return reservationRepository.save(reservation)
    }

    fun deleteReservation(id: Long) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id)
        } else {
            throw IllegalArgumentException("Reservation not found")
        }
    }
}
