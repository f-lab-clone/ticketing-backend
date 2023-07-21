package com.group4.ticketingservice.repository

import com.group4.ticketingservice.entity.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepository : JpaRepository<Reservation, Long>{
    fun findByUserId(userId: Int): Reservation
}
