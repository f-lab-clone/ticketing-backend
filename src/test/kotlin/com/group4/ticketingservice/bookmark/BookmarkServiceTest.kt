package com.group4.ticketingservice.bookmark

import com.group4.ticketingservice.dto.BookmarkFromdto
import com.group4.ticketingservice.entity.Bookmark
import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.BookmarkRepository
import com.group4.ticketingservice.repository.EventRepository
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.service.BookmarkService
import com.group4.ticketingservice.utils.Authority
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.modelmapper.ModelMapper
import java.time.OffsetDateTime

class BookmarkServiceTest() {
    private val userRepository: UserRepository = mockk()
    private val eventRepository: EventRepository = mockk()
    private val repository: BookmarkRepository = mockk()
    private val modelMapper: ModelMapper = ModelMapper()
    private val bookmarkService: BookmarkService = BookmarkService(userRepository, eventRepository, repository)

    val sampleUser = User(
        name = "james",
        email = "james@example.com",
        password = "12345678",
        authority = Authority.USER,
        phone = "010-1234-5678"
    )

    val sampleUserId = 1

    private val sampleEvent: Event = Event(
        id = 1,
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

    private val sampleBookmarkDto = BookmarkFromdto(
        event_id = 1
    )

    @Test
    fun `bookmarkService_getList() invoke repository_findByUser`() {
        // given

        every { repository.findByUserId(sampleUserId) } returns listOf(sampleBookmark)

        // when
        bookmarkService.getList(sampleUserId)

        // then
        verify(exactly = 1) { repository.findByUserId(sampleUserId) }
    }

    @Test
    fun `bookmarkService_getList() should return emptyList`() {
        // given
        every { repository.findByUserId(sampleUserId) } returns listOf()

        // when
        val result: List<Bookmark> = bookmarkService.getList(sampleUserId)

        // then
        verify(exactly = 1) { repository.findByUserId(sampleUserId) }
        assert(result == listOf<Bookmark>())
    }

    @Test
    fun `bookmarkService_get() invoke repository_findByIdAndUser`() {
        // given
        every { repository.findByIdAndUserId(1, sampleUserId) } returns sampleBookmark

        // when
        val result: Bookmark? = bookmarkService.get(sampleUserId, 1)

        // then
        verify(exactly = 1) { repository.findByIdAndUserId(1, sampleUserId) }
        assert(result == sampleBookmark)
    }

    @Test
    fun `bookmarkService_create() invoke repository_save`() {
        // given
        every { userRepository.getReferenceById(any()) } returns sampleUser
        every { eventRepository.getReferenceById(any()) } returns sampleEvent
        every { repository.save(any()) } returns sampleBookmark

        // when
        bookmarkService.create(sampleUserId, sampleBookmarkDto)

        // then
        verify(exactly = 1) { repository.save(any()) }
    }

    @Test
    fun `bookmarkService_delete() invoke repository_deleteByIdAndUser`() {
        // given

        every { repository.deleteByIdAndUserId(1, sampleUserId) } returns Unit

        // when
        bookmarkService.delete(sampleUserId, 1)

        // then
        verify(exactly = 1) { repository.deleteByIdAndUserId(1, sampleUserId) }
    }
}
