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
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.modelmapper.ModelMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
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
        authority = Authority.USER
    )

    val sampleUserId = 1

    private val sampleEvent: Event = Event(
        id = 1,
        title = "test title",
        date = OffsetDateTime.now(),
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

    val pageable: Pageable = PageRequest.of(0, 4)
    val content = mutableListOf(
        Bookmark(
            id = 11,
            user = sampleUser,
            event = Event(
                id = 1,
                title = "정섭이의 코딩쇼",
                date = OffsetDateTime.now(),
                reservationEndTime = OffsetDateTime.now(),
                reservationStartTime = OffsetDateTime.now(),
                maxAttendees = 10
            )
        ),
        Bookmark(
            id = 12,
            user = sampleUser,
            event = Event(
                id = 2,
                title = "민준이의 전국군가잘함",
                date = OffsetDateTime.now(),
                reservationEndTime = OffsetDateTime.now(),
                reservationStartTime = OffsetDateTime.now(),
                maxAttendees = 10
            )
        ),
        Bookmark(
            id = 13,
            user = sampleUser,
            event = Event(
                id = 3,
                title = "하영이의 신작도서 팬싸인회",
                date = OffsetDateTime.now(),
                reservationEndTime = OffsetDateTime.now(),
                reservationStartTime = OffsetDateTime.now(),
                maxAttendees = 10
            )
        ),
        Bookmark(
            id = 14,
            user = sampleUser,
            event = Event(
                id = 4,
                title = "준하의 스파르타 코딩 동아리 설명회",
                date = OffsetDateTime.now(),
                reservationEndTime = OffsetDateTime.now(),
                reservationStartTime = OffsetDateTime.now(),
                maxAttendees = 10
            )
        )
    )
    val totalElements: Long = 100
    val page: Page<Bookmark> = PageImpl(content, pageable, totalElements)

    @Test
    fun `bookmarkService_getList() invoke repository_findByUser`() {
        // given

        every { repository.findByUserId(sampleUserId, pageable) } returns page

        // when
        bookmarkService.getBookmarks(sampleUserId, pageable)

        // then
        verify(exactly = 1) { repository.findByUserId(sampleUserId, pageable) }
    }

    @Test
    fun `bookmarkService_getBookmarks() return page`() {
        // given
        every { repository.findByUserId(sampleUserId, pageable) } returns page

        // when
        val result: Page<Bookmark> = bookmarkService.getBookmarks(sampleUserId, pageable)

        // then
        assertThat(result.totalElements).isEqualTo(totalElements)
        assertThat(result.numberOfElements).isEqualTo(content.size)
        assertThat(result.content[0].id).isEqualTo(content[0].id)
        assertThat(result.content[1].id).isEqualTo(content[1].id)
    }

    @Test
    fun `bookmarkService_getList() should return emptyList`() {
        // given
        every { repository.findByUserId(sampleUserId, pageable) } returns page

        // when
        val result = bookmarkService.getBookmarks(sampleUserId, pageable)

        // then
        verify(exactly = 1) { repository.findByUserId(sampleUserId, pageable) }
        assert(result == page)
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
