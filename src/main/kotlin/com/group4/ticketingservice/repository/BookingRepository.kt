package com.group4.ticketingservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import com.group4.ticketingservice.model.Booking

interface BookingRepository : JpaRepository<Booking, Long>
