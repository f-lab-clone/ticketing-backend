package com.group4.ticketingservice.repository

import com.group4.ticketingservice.entity.Bookmark
import com.group4.ticketingservice.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookmarkRepository : JpaRepository<Bookmark, Long> {
    fun findByIdAndUser(id: Int, username: User): Bookmark
    fun deleteByIdAndUser(id: Int, username: User)
    fun findByUser(user: User): List<Bookmark>
}
