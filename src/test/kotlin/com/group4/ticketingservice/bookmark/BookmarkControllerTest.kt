package com.group4.ticketingservice.bookmark

import com.group4.ticketingservice.controller.BookmarkController
import com.group4.ticketingservice.dto.BookmarkFromdto
import com.group4.ticketingservice.entity.Bookmark
import com.group4.ticketingservice.service.BookmarkService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(BookmarkController::class)
class BookmarkControllerTest(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    private lateinit var service: BookmarkService
    private val sampleBookmark = Bookmark(
        user_id = 1,
        show_id = 1
    )
    private val sampleBookmarkDto = BookmarkFromdto(
        user_id = 1,
        show_id = 1
    )

    @Test
    fun `POST_api_bookmark should invoke service_create`() {
        every { service.create(sampleBookmarkDto) } returns 1

        mockMvc.perform(
            post("/bookmark")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("user_id", sampleBookmark.user_id.toString())
                .param("show_id", sampleBookmark.show_id.toString())
        )
            .andExpect(status().isOk)

        verify(exactly = 1) { service.create(sampleBookmarkDto) }
    }

    @Test
    fun `POST_api_bookmark should return HTTP ERROR 400 for invalid parameter`() {
        mockMvc.perform(
            post("/bookmark")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        )
            .andExpect(status().is4xxClientError)
    }

    @Test
    fun `GET_api_bookmarks should invoke service_getList`() {
        every { service.getList() } returns mutableListOf(sampleBookmark)

        mockMvc.perform(MockMvcRequestBuilders.get("/bookmark/list"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].user_id").value(sampleBookmark.user_id))

        verify(exactly = 1) { service.getList() }
    }

    @Test
    fun `GET_api_bookmark should invoke service_get`() {
        every { service.get(1) } returns sampleBookmark

        mockMvc.perform(MockMvcRequestBuilders.get("/bookmark/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.user_id").value(sampleBookmark.user_id))

        verify(exactly = 1) { service.get(1) }
    }

    @Test
    fun `GET_api_bookmark should return HTTP ERROR 404 if element is not found`() {
        every { service.get(1000) } throws NoSuchElementException()

        mockMvc.perform(MockMvcRequestBuilders.get("/bookmark/1000"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `DELETE_api_bookmark_{bookmarkId} should invoke service_delete`() {
        every { service.delete(1) } returns Unit

        mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/bookmark/1")
        )
            .andExpect(status().isOk)

        verify(exactly = 1) { service.delete(1) }
    }
}
