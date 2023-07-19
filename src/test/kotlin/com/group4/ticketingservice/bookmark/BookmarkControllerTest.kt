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
import org.springframework.test.web.servlet.ResultActions
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
        // given
        every { service.create(sampleBookmarkDto) } returns 1

        // when
        val resultActions: ResultActions = mockMvc.perform(
            post("/bookmarks")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("user_id", sampleBookmark.user_id.toString())
                .param("show_id", sampleBookmark.show_id.toString())
        )

        // then
        resultActions.andExpect(status().isCreated)

        verify(exactly = 1) { service.create(sampleBookmarkDto) }
    }

    @Test
    fun `GET_api_bookmarks should invoke service_getList`() {
        // given
        every { service.getList() } returns mutableListOf(sampleBookmark)

        // when
        val resultActions: ResultActions = mockMvc.perform(MockMvcRequestBuilders.get("/bookmarks"))

        // then
        resultActions.andExpect(status().isOk)
            .andExpect(jsonPath("$[0].user_id").value(sampleBookmark.user_id))

        verify(exactly = 1) { service.getList() }
    }

    @Test
    fun `GET_api_bookmark should invoke service_get`() {
        // given
        every { service.get(1) } returns sampleBookmark

        // when
        val resultActions: ResultActions = mockMvc.perform(MockMvcRequestBuilders.get("/bookmarks/1"))

        // then
        resultActions.andExpect(status().isOk)
            .andExpect(jsonPath("$.user_id").value(sampleBookmark.user_id))

        verify(exactly = 1) { service.get(1) }
    }

    @Test
    fun `DELETE_api_bookmark_{bookmarkId} should invoke service_delete`() {
        // given
        every { service.delete(1) } returns Unit

        // when
        val resultActions: ResultActions = mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/bookmarks/1")
        )

        // then
        resultActions.andExpect(status().isNoContent)

        verify(exactly = 1) { service.delete(1) }
    }
}
