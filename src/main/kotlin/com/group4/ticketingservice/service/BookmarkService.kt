package com.group4.ticketingservice.service

import com.group4.ticketingservice.dto.BookmarkFromdto
import com.group4.ticketingservice.entity.Bookmark
import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.BookmarkRepository
import com.group4.ticketingservice.repository.EventRepository
import com.group4.ticketingservice.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookmarkService @Autowired constructor(
    val userRepository: UserRepository,
    val eventRepository: EventRepository,
    val bookmarkRepository: BookmarkRepository
) {

    fun create(username: String, bookmarkFormDto: BookmarkFromdto): Int? {
        val user: User = userRepository.findByEmail(username)
            ?: throw IllegalArgumentException("User not found")
        val event: Event = eventRepository.findById(bookmarkFormDto.show_id.toLong()).orElseThrow {
            IllegalArgumentException("Event not found")
        }

        val bookmark = Bookmark(user = user, event = event)

        return bookmarkRepository.save(bookmark).id
    }

    fun get(username: String, id: Int): Bookmark? {
        val user: User = userRepository.findByEmail(username)
            ?: throw IllegalArgumentException("User not found")

        return bookmarkRepository.findByIdAndUser(id, user)
    }

    fun delete(username: String, id: Int) {
        val user: User = userRepository.findByEmail(username)
            ?: throw IllegalArgumentException("User not found")

        bookmarkRepository.deleteByIdAndUser(id, user)
    }

    fun getList(username: String): List<Bookmark> {
        val user: User = userRepository.findByEmail(username)
            ?: throw IllegalArgumentException("User not found")
        return bookmarkRepository.findByUser(user)
    }
}
