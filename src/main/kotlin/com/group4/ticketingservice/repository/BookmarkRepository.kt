package com.group4.ticketingservice.repository

import com.group4.ticketingservice.entity.Bookmark
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookmarkRepository : JpaRepository<Bookmark, Int> {
    fun findByIdAndUserId(id: Int, userId: Int): Bookmark?
    fun deleteByIdAndUserId(id: Int, userId: Int)
    fun findByUserId(userId: Int, pageable: Pageable): Page<Bookmark>
}
