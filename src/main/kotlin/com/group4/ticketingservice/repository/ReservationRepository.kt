package com.group4.ticketingservice.repository

import com.group4.ticketingservice.entity.Reservation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepository : JpaRepository<Reservation, Int> {
    fun findByUserId(userId: Int, pageable: Pageable): Page<Reservation>
}
