package com.group4.ticketingservice.repository

import com.group4.ticketingservice.entity.Event
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface EventRepository : JpaRepository<Event, Int>, JpaSpecificationExecutor<Event> {
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select e from Event e where e.id = :id")
    fun findByIdWithPesimisticLock(id: Int): Event?

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select e from Event e where e.id = :id")
    fun findByIdWithOptimisicLock(id: Int): Event?
}
