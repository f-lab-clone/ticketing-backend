package com.group4.ticketingservice.repository

import com.group4.ticketingservice.entity.Stage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StageRepository : JpaRepository<Stage, Long>
