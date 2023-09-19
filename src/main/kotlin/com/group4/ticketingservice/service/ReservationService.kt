package com.group4.ticketingservice.service

import com.group4.ticketingservice.entity.Reservation
import com.group4.ticketingservice.repository.EventRepository
import com.group4.ticketingservice.repository.ReservationRepository
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.utils.exception.CustomException
import com.group4.ticketingservice.utils.exception.ErrorCodes
import java.time.OffsetDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReservationService @Autowired constructor(
    private val userRepository: UserRepository,
    private val eventRepository: EventRepository,
    private val reservationRepository: ReservationRepository
) {
    @Transactional
    fun createReservation(eventId: Int, userId: Int): Reservation {
        val user = userRepository.getReferenceById(userId)
        val event = eventRepository.findByIdWithPesimisticLock(eventId) ?: throw CustomException(ErrorCodes.EVENT_NOT_FOUND)

        val reservation = Reservation(user = user, event = event, bookedAt = OffsetDateTime.now())

        if (event.maxAttendees > event.currentReservationCount) {
            event.currentReservationCount += 1
            eventRepository.saveAndFlush(event)

            reservationRepository.saveAndFlush(reservation)
        } else {
            throw CustomException(ErrorCodes.EVENT_ALREADY_RESERVED_ALL)
        }
        return reservation
    }

    fun getReservation(reservationId: Int): Reservation {
        return reservationRepository.findById(reservationId).orElseThrow {
            CustomException(ErrorCodes.RESERVATION_NOT_FOUND)
        }
    }

    fun updateReservation(reservationId: Int, eventId: Int): Reservation {
        val reservation: Reservation = reservationRepository.findById(reservationId).orElseThrow {
            CustomException(ErrorCodes.RESERVATION_NOT_FOUND)
        }
        val event = eventRepository.findById(eventId).orElseThrow {
            CustomException(ErrorCodes.EVENT_NOT_FOUND)
        }
        reservation.event = event

        return reservationRepository.save(reservation)
    }

    fun deleteReservation(userId: Int, id: Int) {
        val reservation = reservationRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCodes.RESERVATION_NOT_FOUND)
        if (reservation.user.id != userId) throw CustomException(ErrorCodes.NOT_OWNER_OF_RESERVATION)
        reservationRepository.delete(reservation)
    }
}
