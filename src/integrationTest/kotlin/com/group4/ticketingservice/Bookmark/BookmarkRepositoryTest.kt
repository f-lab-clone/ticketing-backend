package com.group4.ticketingservice

import com.group4.ticketingservice.entity.Bookmark
import com.group4.ticketingservice.repository.BookmarkRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class BookmarkRepositoryTest(
    @Autowired val bookmarkRepository: BookmarkRepository
) {

    @Test
    fun `bookmarkRepository_save should return savedBookmark`() {
        // given
        val sampleBookmark = Bookmark(
            user_id = 1,
            show_id = 1
        )

        // when
        val savedBookmark = bookmarkRepository.save(sampleBookmark)

        // then
        assertThat(savedBookmark).isEqualTo(sampleBookmark)
    }

    @Test
    fun `bookmarkRepository_findByIdOrNull should return foundBookmark`() {
        // given
        val sampleBookmark = Bookmark(
            user_id = 1,
            show_id = 1
        )

        val savedBookmark = bookmarkRepository.save(sampleBookmark)

        // when
        val foundBookmark = bookmarkRepository.findByIdOrNull(savedBookmark.id?.toLong())

        // then
        assert(savedBookmark.id == foundBookmark?.id)
    }

    @Test
    fun `bookmarkRepository_deleteById should delete requestedBookmark`() {
        // given
        val sampleBookmark = Bookmark(
            user_id = 1,
            show_id = 1
        )

        val savedBookmark = bookmarkRepository.save(sampleBookmark)

        // when
        savedBookmark.id?.let { bookmarkRepository.deleteById(it.toLong()) }

        // then
        val deletedBookmark = bookmarkRepository.findByIdOrNull(savedBookmark.id?.toLong())

        assertThat(deletedBookmark).isEqualTo(null)
    }

    @Test
    fun `bookmarkRepository_findAll should return list of Bookmarks`() {
        // given
        val sampleBookmark = Bookmark(
            user_id = 1,
            show_id = 1
        )

        bookmarkRepository.save(sampleBookmark)

        // when
        val listofBookmarks = bookmarkRepository.findAll()

        // then
        assertInstanceOf(ArrayList::class.java, listofBookmarks)
        assertInstanceOf(Bookmark::class.java, listofBookmarks[0])
    }
}
