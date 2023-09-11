package com.group4.ticketingservice.repository

import com.group4.ticketingservice.entity.Bookmark
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookmarkRepository : JpaRepository<Bookmark, Long> {
    fun findByIdAndUserId(id: Int, userId: Long): Bookmark
    fun deleteByIdAndUserId(id: Int, userId: Long)
    fun findByUserId(userId: Long): List<Bookmark>
}
