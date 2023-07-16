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
        every { repository.findAll() } returns listOf(EmptyBookmark)

        bookmarkService.getList()

        verify(exactly = 1) { repository.findAll() }
    }

    @Test
    fun `bookmarkService_getList() should return emptyList`() {
        every { repository.findAll() } returns listOf()

        assert(bookmarkService.getList() == listOf<Bookmark>())
    }

    @Test
    fun `bookmarkService_get() invoke repository_findById`() {
        every { repository.findByIdOrNull(1) } returns sampleBookmark

        bookmarkService.get(1)

        verify(exactly = 1) { repository.findById(1) }
    }

    @Test
    fun `bookmarkService_create() invoke repository_save`() {
        every { repository.save(any()) } returns sampleBookmark

        bookmarkService.create(sampleBookmarkDto)

        verify(exactly = 1) { repository.save(any()) }
    }

    @Test
    fun `bookmarkService_delete() invoke repository_deleteById`() {
        every { repository.deleteById(1) } returns Unit

        bookmarkService.delete(1)
        verify(exactly = 1) { repository.deleteById(1) }
    }
}
