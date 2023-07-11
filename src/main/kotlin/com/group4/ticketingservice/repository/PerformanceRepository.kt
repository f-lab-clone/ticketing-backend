package com.group4.ticketingservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import com.group4.ticketingservice.model.Performance

interface PerformanceRepository: JpaRepository<Performance, Long>
