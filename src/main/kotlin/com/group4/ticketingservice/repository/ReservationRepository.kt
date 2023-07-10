package com.group4.ticketingservice.repository

import com.group4.ticketingservice.domain.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface ReservationRepository : JpaRepository<Reservation, Long>