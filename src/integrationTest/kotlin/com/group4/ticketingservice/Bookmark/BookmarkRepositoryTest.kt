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
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import java.time.Duration.ofHours
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

    val sampleEvents = mutableListOf(
        Event(
            title = "정섭이의 코딩쇼",
            startDate = OffsetDateTime.now(),
            endDate = OffsetDateTime.now(),
            reservationEndTime = OffsetDateTime.now() + ofHours(2),
            reservationStartTime = OffsetDateTime.now() - ofHours(2),
            maxAttendees = 10
        ),
        Event(
            title = "민준이의 전국군가잘함",
            startDate = OffsetDateTime.now(),
            endDate = OffsetDateTime.now(),
            reservationEndTime = OffsetDateTime.now() + ofHours(2),
            reservationStartTime = OffsetDateTime.now() - ofHours(2),
            maxAttendees = 10
        ),
        Event(
            title = "하영이의 신작도서 팬싸인회",
            startDate = OffsetDateTime.now(),
            endDate = OffsetDateTime.now(),
            reservationEndTime = OffsetDateTime.now() + ofHours(2),
            reservationStartTime = OffsetDateTime.now() - ofHours(2),
            maxAttendees = 10
        ),
        Event(
            title = "준하의 스파르타 코딩 동아리 설명회",
            startDate = OffsetDateTime.now(),
            endDate = OffsetDateTime.now(),
            reservationEndTime = OffsetDateTime.now() + ofHours(2),
            reservationStartTime = OffsetDateTime.now() - ofHours(2),
            maxAttendees = 10
        ),
        Event(
            title = "군대에서 코딩 직군으로 복무하기 설명회",
            startDate = OffsetDateTime.now(),
            endDate = OffsetDateTime.now(),
            reservationEndTime = OffsetDateTime.now() + ofHours(2),
            reservationStartTime = OffsetDateTime.now() - ofHours(2),
            maxAttendees = 10
        )
    )

    @AfterEach
    fun removeBookmark() {
        bookmarkRepository.deleteAll()
    }

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
        assert(savedBookmark.id == foundBookmark?.id)
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
        val pageable: Pageable = PageRequest.of(0, 10)

        // when
        val listofBookmarks = bookmarkRepository.findByUserId(savedUser.id!!, pageable)

        // then
        assertInstanceOf(Page::class.java, listofBookmarks)
        assertInstanceOf(Bookmark::class.java, listofBookmarks.content[0])
    }

    @Test
    fun `bookmarkRepository_findByUser should return page of bookmarks with pagination`() {
        // given
        val savedUser = userRepository.save(sampleUser)
        bookmarkRepository.deleteAll()
        sampleEvents.forEach {
            val savedEvent = eventRepository.save(it)
            bookmarkRepository.save(Bookmark(user = savedUser, event = savedEvent))
        }
        val pageSize = 2
        val pageable: Pageable = PageRequest.of(0, pageSize)

        // when
        val result = bookmarkRepository.findByUserId(savedUser.id!!, pageable)

        // then
        assertThat(result.totalElements).isEqualTo(sampleEvents.size.toLong())
        assertThat(result.pageable.pageSize).isEqualTo(pageSize)
        assertThat(result.numberOfElements).isEqualTo(pageSize)
        assertThat(result.content.size).isEqualTo(pageSize)
    }
}
