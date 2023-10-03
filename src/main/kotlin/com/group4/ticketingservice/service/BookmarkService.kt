package com.group4.ticketingservice.service

import com.group4.ticketingservice.dto.BookmarkFromdto
import com.group4.ticketingservice.entity.Bookmark
import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.BookmarkRepository
import com.group4.ticketingservice.repository.EventRepository
import com.group4.ticketingservice.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookmarkService @Autowired constructor(
    val userRepository: UserRepository,
    val eventRepository: EventRepository,
    val bookmarkRepository: BookmarkRepository
) {

    fun create(userId: Int, bookmarkFormDto: BookmarkFromdto): Bookmark {
        val user: User = userRepository.getReferenceById(userId)

        val event: Event = eventRepository.getReferenceById(bookmarkFormDto.event_id!!)

        val bookmark = Bookmark(user = user, event = event)

        return bookmarkRepository.save(bookmark)
    }

    fun get(userId: Int, id: Int): Bookmark? {
        return bookmarkRepository.findByIdAndUserId(id, userId)
    }

    fun delete(userId: Int, id: Int) {
        bookmarkRepository.deleteByIdAndUserId(id, userId)
    }

    fun getBookmarks(userId: Int, pageable: Pageable): Page<Bookmark> {
        return bookmarkRepository.findByUserId(userId, pageable)
    }
}
