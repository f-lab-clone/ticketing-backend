package com.group4.ticketingservice.bookmark

import com.group4.ticketingservice.dto.BookmarkFromdto
import com.group4.ticketingservice.entity.Bookmark
import com.group4.ticketingservice.repository.BookmarkRepository
import com.group4.ticketingservice.service.BookmarkService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.modelmapper.ModelMapper
import org.springframework.data.repository.findByIdOrNull

class BookmarkServiceTest {
    private val repository: BookmarkRepository = mockk()
    private val modelMapper: ModelMapper = ModelMapper()
    private val bookmarkService: BookmarkService = BookmarkService(repository, modelMapper)

    val EmptyBookmark = Bookmark(
        id = -1,
        user_id = -1,
        show_id = -1
    )
    private val sampleBookmark = Bookmark(
        user_id = 1,
        show_id = 1
    )

    private val sampleBookmarkDto = BookmarkFromdto(
        user_id = 1,
        show_id = 1
    )

    @Test
    fun `bookmarkService_getList() invoke repository_findAll`() {
        // given
        every { repository.findAll() } returns listOf(EmptyBookmark)

        // when
        bookmarkService.getList()

        // then
        verify(exactly = 1) { repository.findAll() }
    }

    @Test
    fun `bookmarkService_getList() should return emptyList`() {
        // given
        every { repository.findAll() } returns listOf()

        // when
        val result: List<Bookmark> = bookmarkService.getList()

        // then
        verify(exactly = 1) { repository.findAll() }
        assert(result == listOf<Bookmark>())
    }

    @Test
    fun `bookmarkService_get() invoke repository_findById`() {
        // given
        every { repository.findByIdOrNull(1) } returns sampleBookmark

        // when
        val result: Bookmark? = bookmarkService.get(1)

        // then
        verify(exactly = 1) { repository.findById(1) }
        assert(result == sampleBookmark)
    }

    @Test
    fun `bookmarkService_create() invoke repository_save`() {
        // given
        every { repository.save(any()) } returns sampleBookmark

        // when
        bookmarkService.create(sampleBookmarkDto)

        // then
        verify(exactly = 1) { repository.save(any()) }
    }

    @Test
    fun `bookmarkService_delete() invoke repository_deleteById`() {
        // given
        every { repository.deleteById(1) } returns Unit

        // when
        bookmarkService.delete(1)

        // then
        verify(exactly = 1) { repository.deleteById(1) }
    }
}
