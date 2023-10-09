package com.group4.ticketingservice.Bookmark

import com.group4.ticketingservice.AbstractIntegrationTest
import com.group4.ticketingservice.entity.Bookmark
import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.BookmarkRepository
import com.group4.ticketingservice.repository.EventRepository
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.utils.Authority
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

class BookmarkRepositoryTest(
    @Autowired val bookmarkRepository: BookmarkRepository,
    @Autowired val userRepository: UserRepository,
    @Autowired val eventRepository: EventRepository
) : AbstractIntegrationTest() {

    val sampleUser = User(
        name = "james",
        email = "james@example.com",
        password = "12345678",
        authority = Authority.USER,
        phone = "010-1234-5678"
    )

    private val sampleEvent: Event = Event(
        title = "test title",
        startDate = OffsetDateTime.now(),
        endDate = OffsetDateTime.now(),

        reservationEndTime = OffsetDateTime.now(),
        reservationStartTime = OffsetDateTime.now(),
        maxAttendees = 10
    )

    private val sampleBookmark = Bookmark(
        user = sampleUser,
        event = sampleEvent
    )

    @Test
    fun `bookmarkRepository_save should return savedBookmark`() {
        // given
        val savedUser = userRepository.save(sampleUser)
        val savedEvent = eventRepository.save(sampleEvent)
        val requestBookmark = Bookmark(user = savedUser, event = savedEvent)

        // when
        val savedBookmark = bookmarkRepository.save(requestBookmark)

        // then
        assertThat(savedBookmark).isEqualTo(requestBookmark)
    }

    @Test
    fun `bookmarkRepository_findByIdAndUser should return foundBookmark`() {
        // given
        val savedUser = userRepository.save(sampleUser)
        val savedEvent = eventRepository.save(sampleEvent)
        val savedBookmark = bookmarkRepository.save(Bookmark(user = savedUser, event = savedEvent))

        // when
        val foundBookmark = bookmarkRepository.findByIdAndUserId(savedBookmark.id!!, savedUser.id!!)

        // then
        assert(savedBookmark.id == foundBookmark.id)
    }

    @Test
    @Transactional
    fun `bookmarkRepository_deleteByIdAndUser should delete requestedBookmark`() {
        // given
        val savedUser = userRepository.save(sampleUser)
        val savedEvent = eventRepository.save(sampleEvent)
        val savedBookmark = bookmarkRepository.save(Bookmark(user = savedUser, event = savedEvent))

        // when
        bookmarkRepository.deleteByIdAndUserId(savedBookmark.id!!, savedUser.id!!)

        // then
        val deletedBookmark = bookmarkRepository.findByIdOrNull(savedBookmark.id)

        assertThat(deletedBookmark).isEqualTo(null)
    }

    @Test
    fun `bookmarkRepository_findByUser should return list of Bookmarks`() {
        // given
        val savedUser = userRepository.save(sampleUser)
        val savedEvent = eventRepository.save(sampleEvent)
        bookmarkRepository.save(Bookmark(user = savedUser, event = savedEvent))

        // when
        val listofBookmarks = bookmarkRepository.findByUserId(savedUser.id!!)

        // then
        assertInstanceOf(ArrayList::class.java, listofBookmarks)
        assertInstanceOf(Bookmark::class.java, listofBookmarks[0])
    }
}
