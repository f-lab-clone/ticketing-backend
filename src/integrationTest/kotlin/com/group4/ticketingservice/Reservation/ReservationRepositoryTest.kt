package com.group4.ticketingservice

import com.group4.ticketingservice.entity.Reservation
import com.group4.ticketingservice.repository.ReservationRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull

class ReservationRepositoryTest(
    @Autowired val reservationRepository: ReservationRepository
) : AbstractIntegrationTest() {

    @Test
    fun `reservationRepository_save should return savedReservation`() {
        // given
        val sampleReservation = Reservation(1)

        // when
        val savedBookmark = reservationRepository.save(sampleReservation)

        // then
        assertThat(savedBookmark).isEqualTo(sampleReservation)
    }

    @Test
    fun `reservationRepository_findByIdOrNull should return foundReservation`() {
        // given
        val sampleReservation = Reservation(1)

        val savedReservation = reservationRepository.save(sampleReservation)

        // when
        val foundBookmark = reservationRepository.findByIdOrNull(savedReservation.userId?.toLong())

        // then
        assert(savedReservation.userId == foundBookmark?.userId)
    }

//    @Test
//    fun `bookmarkRepository_deleteById should delete requestedBookmark`() {
//        // given
//        val sampleReservation = Reservation(1)
//
//        val savedBookmark = reservationRepository.save(sampleReservation)
//
//        // when
//        savedBookmark.id?.let { reservationRepository.deleteById(it.toLong()) }
//
//        // then
//        val deletedReservation = reservationRepository.findByIdOrNull(savedBookmark.id?.toLong())
//
//        assertThat(deletedReservation).isEqualTo(null)
//    }

    @Test
    fun `reservationRepository_findAll should return list of Reservations`() {
        // given
        val sampleReservation = Reservation(1)

        reservationRepository.save(sampleReservation)

        // when
        val listofReservations = reservationRepository.findAll()

        // then
        assertInstanceOf(ArrayList::class.java, listofReservations)
        assertInstanceOf(Reservation::class.java, listofReservations[0])
    }
}
