package com.group4.ticketingservice.repository

import com.group4.ticketingservice.entity.SeatReservationMap
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SeatReservationMapRepository : JpaRepository<SeatReservationMap, Long>
