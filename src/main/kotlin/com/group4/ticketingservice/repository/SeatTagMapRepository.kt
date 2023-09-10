package com.group4.ticketingservice.repository

import com.group4.ticketingservice.entity.SeatTagMap
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SeatTagMapRepository : JpaRepository<SeatTagMap, Long>
