package com.group4.ticketingservice.bookmark

import com.group4.ticketingservice.entity.Bookmark
import com.group4.ticketingservice.repository.BookmarkRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class BookmarkRepositoryTest(
    @Autowired val bookmarkRepository: BookmarkRepository
) {

    @Test
    fun `bookmarkRepository_save should return savedBookmark`() {
        val sampleBookmark = Bookmark(
            user_id = 1,
            show_id = 1
        )

        val savedBookmark = bookmarkRepository.save(sampleBookmark)

        assertThat(savedBookmark).isEqualTo(sampleBookmark)
    }

    @Test
    fun `bookmarkRepository_findByIdOrNull should return foundBookmark`() {
        val sampleBookmark = Bookmark(
            user_id = 1,
            show_id = 1
        )

        val savedBookmark = bookmarkRepository.save(sampleBookmark)

        val foundBookmark = bookmarkRepository.findByIdOrNull(savedBookmark.id?.toLong())

        assertThat(foundBookmark).isEqualTo(savedBookmark)
    }

    @Test
    fun `bookmarkRepository_deleteById should delete requestedBookmark`() {
        val sampleBookmark = Bookmark(
            user_id = 1,
            show_id = 1
        )

        val savedBookmark = bookmarkRepository.save(sampleBookmark)

        savedBookmark.id?.let { bookmarkRepository.deleteById(it.toLong()) }

        val deletedBookmark = bookmarkRepository.findByIdOrNull(savedBookmark.id?.toLong())

        assertThat(deletedBookmark).isEqualTo(null)
    }

    @Test
    fun `bookmarkRepository_findAll should return list of Bookmarks`() {
        val sampleBookmark = Bookmark(
            user_id = 1,
            show_id = 1
        )

        bookmarkRepository.save(sampleBookmark)

        val listofBookmarks = bookmarkRepository.findAll()

        assertInstanceOf(ArrayList::class.java, listofBookmarks)
        assertInstanceOf(Bookmark::class.java, listofBookmarks[0])
    }
}
