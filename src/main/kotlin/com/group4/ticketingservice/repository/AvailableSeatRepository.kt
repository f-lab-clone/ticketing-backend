package com.group4.ticketingservice.repository

import com.group4.ticketingservice.entity.AvailableSeat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AvailableSeatRepository : JpaRepository<AvailableSeat, Long>
