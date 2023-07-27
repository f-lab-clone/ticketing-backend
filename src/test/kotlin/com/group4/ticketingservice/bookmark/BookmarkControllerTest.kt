package com.group4.ticketingservice.bookmark

import com.group4.ticketingservice.filter.JwtAuthenticationFilter
import com.group4.ticketingservice.config.SecurityConfig
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
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [BookmarkController::class])
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
    @WithMockUser(roles = ["USER"])
    fun `POST_api_bookmark should invoke service_create`() {
        // given
        every { service.create(sampleBookmarkDto) } returns 1

        // when
        mockMvc.perform(
                post("/bookmarks")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("user_id", sampleBookmark.user_id.toString())
                        .param("show_id", sampleBookmark.show_id.toString())
        )

        // then
        verify(exactly = 1) { service.create(sampleBookmarkDto) }
    }

    @Test
    @WithMockUser(roles = ["USER"])
    fun `POST_api_bookmark should return saved bookmark id with HTTP 201 Created`() {
        // given
        every { service.create(sampleBookmarkDto) } returns 1

        // when
        val resultActions: ResultActions = mockMvc.perform(
                post("/bookmarks")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("user_id", sampleBookmark.user_id.toString())
                        .param("show_id", sampleBookmark.show_id.toString())
        )

        // then
        resultActions.andExpect(status().isCreated)
                .andExpect(content().json("1"))
    }

    @Test
    @WithMockUser(roles = ["USER"])
    fun `POST_api_bookmark should return HTTP ERROR 400 for invalid parameter`() {
        // given
        every { service.create(sampleBookmarkDto) } returns 1

        // when
        val resultActions: ResultActions = mockMvc.perform(
                post("/bookmarks")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("user_id", sampleBookmark.user_id.toString())
                        .param("show_id", sampleBookmark.show_id.toString())
        )

        // then
        resultActions.andExpect(status().isCreated)
                .andExpect(content().json("1"))
    }

    @Test
    @WithMockUser(roles = ["USER"])
    fun `GET_api_bookmarks should invoke service_getList`() {
        // given
        every { service.getList() } returns mutableListOf(sampleBookmark)

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/bookmarks"))

        // then
        verify(exactly = 1) { service.getList() }
    }

    @Test
    @WithMockUser(roles = ["USER"])
    fun `GET_api_bookmarks should return list of bookmarks with HTTP 200 OK`() {
        // given
        every { service.getList() } returns mutableListOf(sampleBookmark)

        // when
        val resultActions: ResultActions = mockMvc.perform(MockMvcRequestBuilders.get("/bookmarks"))

        // then
        resultActions.andExpect(status().isOk)
                .andExpect(jsonPath("$[0].user_id").value(sampleBookmark.user_id))
    }

    @Test
    @WithMockUser(roles = ["USER"])
    fun `GET_api_bookmark should invoke service_get`() {
        // given
        every { service.get(1) } returns sampleBookmark

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/bookmarks/1"))

        // then
        verify(exactly = 1) { service.get(1) }
    }

    @Test
    @WithMockUser(roles = ["USER"])
    fun `GET_api_bookmark should return found bookmark with HTTP 200 OK`() {
        // given
        every { service.get(1) } returns sampleBookmark

        // when
        val resultActions: ResultActions = mockMvc.perform(MockMvcRequestBuilders.get("/bookmarks/1"))

        // then
        resultActions.andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value(sampleBookmark.id))
                .andExpect(jsonPath("$.user_id").value(sampleBookmark.user_id))
                .andExpect(jsonPath("$.show_id").value(sampleBookmark.show_id))
    }

    @Test
    @WithMockUser(roles = ["USER"])
    fun `GET_api_bookmark should return null with HTTP 200 OK if element is not found`() {
        // given
        every { service.get(1) } returns null

        // when
        val resultActions: ResultActions = mockMvc.perform(MockMvcRequestBuilders.get("/bookmarks/1"))

        // then
        resultActions.andExpect(status().isOk)
                .andExpect(content().string("null"))
    }

    @Test
    @WithMockUser(roles = ["USER"])
    fun `DELETE_api_bookmark_{bookmarkId} should invoke service_delete`() {
        // given
        every { service.delete(1) } returns Unit

        // when
        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/bookmarks/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        )

        // then
        verify(exactly = 1) { service.delete(1) }
    }

    @Test
    @WithMockUser(roles = ["USER"])
    fun `DELETE_api_bookmark_{bookmarkId} should return HTTP 204 No Content`() {
        // given
        every { service.delete(1) } returns Unit

        // when
        val resultActions: ResultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/bookmarks/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        )

        // then
        resultActions.andExpect(status().isNoContent)
    }
}
