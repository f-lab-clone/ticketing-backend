package com.group4.ticketingservice.service

import com.group4.ticketingservice.entity.Reservation
import com.group4.ticketingservice.repository.EventRepository
import com.group4.ticketingservice.repository.ReservationRepository
import com.group4.ticketingservice.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
class ReservationService @Autowired constructor(
    private val userRepository: UserRepository,
    private val eventRepository: EventRepository,
    private val reservationRepository: ReservationRepository
) {
    @Transactional
    fun createReservation(eventId: Int, userId: Int): Reservation {
        val user = userRepository.getReferenceById(userId)
        val event = eventRepository.findByIdWithPesimisticLock(eventId) ?: throw RuntimeException("")

        val reservation = Reservation(user = user, event = event, bookedAt = OffsetDateTime.now())

        if (event.maxAttendees > event.currentReservationCount) {
            event.currentReservationCount += 1
            eventRepository.saveAndFlush(event)

            reservationRepository.saveAndFlush(reservation)
        } else {
            throw RuntimeException("")
        }
        return reservation
    }

    fun getReservation(reservationId: Int): Reservation {
        return reservationRepository.findById(reservationId).orElseThrow {
            IllegalArgumentException("Reservation not found")
        }
    }

    fun updateReservation(reservationId: Int, eventId: Int): Reservation {
        val reservation: Reservation = reservationRepository.findById(reservationId).orElseThrow {
            IllegalArgumentException("Reservation not found")
        }
        val event = eventRepository.findById(eventId).orElseThrow {
            IllegalArgumentException("Event not found")
        }
        reservation.event = event

        return reservationRepository.save(reservation)
    }

    fun deleteReservation(userId: Int, id: Int) {
        val reservation = reservationRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("Reservation not found")
        if (reservation.user.id != userId) throw IllegalArgumentException("It's not your reservation")
        reservationRepository.delete(reservation)
    }
}
