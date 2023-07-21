package com.group4.ticketingservice

import com.group4.ticketingservice.entity.Reservation
import com.group4.ticketingservice.repository.ReservationRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
@Transactional
class ReservationJpaTest(@Autowired val reservationRepository: ReservationRepository) {

    @Test
    @DisplayName("Success save data in reservation DB")
    fun save(){
        val reservation = Reservation()

        val savedReservation = reservationRepository.save(reservation)

        Assertions.assertEquals(reservation.userId, savedReservation.userId)
        Assertions.assertEquals(reservation.name, savedReservation.name)
        Assertions.assertEquals(reservation.bookingTime, savedReservation.bookingTime)
    }
    @Test
    @DisplayName("Success read data in reservation DB")
    fun read(){
        val reservation = Reservation(1)
        val savedReservation = reservationRepository.save(reservation)
        if(savedReservation != null){
            val foundReservation = reservationRepository.findByUserId(1)
            Assertions.assertNotNull(foundReservation)
        }else{
            fail("insert data actions is failed")
        }
    }
}