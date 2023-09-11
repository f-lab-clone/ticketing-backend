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

    fun create(userId: Long, bookmarkFormDto: BookmarkFromdto): Int? {
        val user: User = userRepository.getReferenceById(userId)

        val event: Event = eventRepository.getReferenceById(bookmarkFormDto.event_id.toLong())

        val bookmark = Bookmark(user = user, event = event)

        return bookmarkRepository.save(bookmark).id
    }

    fun get(userId: Long, id: Int): Bookmark? {
        return bookmarkRepository.findByIdAndUserId(id, userId)
    }

    fun delete(userId: Long, id: Int) {
        bookmarkRepository.deleteByIdAndUserId(id, userId)
    }

    fun getList(userId: Long): List<Bookmark> {
        return bookmarkRepository.findByUserId(userId)
    }
}
