package com.group4.ticketingservice.bookmark

import com.google.gson.GsonBuilder
import com.group4.ticketingservice.JwtAuthorizationEntryPoint
import com.group4.ticketingservice.bookmark.BookmarkControllerTest.testFields.testUserId
import com.group4.ticketingservice.bookmark.BookmarkControllerTest.testFields.testUserName
import com.group4.ticketingservice.config.SecurityConfig
import com.group4.ticketingservice.controller.BookmarkController
import com.group4.ticketingservice.dto.BookmarkFromdto
import com.group4.ticketingservice.entity.Bookmark
import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.service.BookmarkService
import com.group4.ticketingservice.user.WithAuthUser
import com.group4.ticketingservice.utils.Authority
import com.group4.ticketingservice.utils.TokenProvider
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Duration
import java.time.OffsetDateTime

@ExtendWith(MockKExtension::class)
@WebMvcTest(
    controllers = [BookmarkController::class],
    includeFilters = [ComponentScan.Filter(value = [(SecurityConfig::class), (JwtAuthorizationEntryPoint::class), (TokenProvider::class)], type = FilterType.ASSIGNABLE_TYPE)]
)
class BookmarkControllerTest(
    @Autowired val mockMvc: MockMvc
) {
    @MockkBean
    private lateinit var service: BookmarkService
    object testFields {
        const val testUserId = 1L
        const val testUserName = "james@example.com"
        const val testUserRole = "USER"
        const val password = "12345678"
    }

    private val sampleUser = User(
        name = "james",
        email = "james@example.com",
        password = "12345678",
        authority = Authority.USER
    )

    private val sampleEvent: Event = Event(
        id = 1,
        title = "test title",
        date = OffsetDateTime.now(),
        reservationEndTime = OffsetDateTime.now() + Duration.ofHours(2),
        reservationStartTime = OffsetDateTime.now() + Duration.ofHours(1),
        maxAttendees = 10,
        user = sampleUser
    )

    private val sampleBookmark = Bookmark(
        id = 1,
        user = sampleUser,
        event = sampleEvent
    )

    private val sampleBookmarkDto = BookmarkFromdto(
        event_id = 1
    )

    @Test
    @WithAuthUser(email = testUserName, id = testUserId)
    fun `POST_api_bookmark should invoke service_create`() {
        // given
        every { service.create(testUserId, sampleBookmarkDto) } returns 1

        // when
        mockMvc.perform(
            post("/bookmarks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(GsonBuilder().create().toJson(sampleBookmarkDto).toString())
        )

        // then
        verify(exactly = 1) { service.create(testUserId, sampleBookmarkDto) }
    }

    @Test
    @WithAuthUser(email = testUserName, id = testUserId)
    fun `POST_api_bookmark should return saved bookmark id with HTTP 201 Created`() {
        // given
        every { service.create(testUserId, sampleBookmarkDto) } returns 1

        // when
        val resultActions: ResultActions = mockMvc.perform(
            post("/bookmarks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(GsonBuilder().create().toJson(sampleBookmarkDto).toString())
        )

        // then
        resultActions.andExpect(status().isCreated)
            .andExpect(content().json("1"))
    }

    @Test
    @WithAuthUser(email = testUserName, id = testUserId)
    fun `POST_api_bookmark should return HTTP ERROR 400 for invalid parameter`() {
        // given
        every { service.create(testUserId, sampleBookmarkDto) } returns 1

        // when
        val resultActions: ResultActions = mockMvc.perform(
            post("/bookmarks")
                .contentType(MediaType.APPLICATION_JSON)
        )

        // then
        resultActions.andExpect(status().isBadRequest)
    }

    @Test
    @WithAuthUser(email = testUserName, id = testUserId)
    fun `GET_api_bookmarks should invoke service_getList`() {
        // given
        every { service.getList(testUserId) } returns mutableListOf(sampleBookmark)

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/bookmarks"))

        // then
        verify(exactly = 1) { service.getList(testUserId) }
    }

    @Test
    @WithAuthUser(email = testUserName, id = testUserId)
    fun `GET_api_bookmarks should return list of bookmarks with HTTP 200 OK`() {
        // given
        every { service.getList(testUserId) } returns mutableListOf(sampleBookmark)

        // when
        val resultActions: ResultActions = mockMvc.perform(MockMvcRequestBuilders.get("/bookmarks"))

        // then
        resultActions.andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(1))
    }

    @Test
    @WithAuthUser(email = testUserName, id = testUserId)
    fun `GET_api_bookmark should invoke service_get`() {
        // given
        every { service.get(testUserId, 1) } returns sampleBookmark

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/bookmarks/1"))

        // then
        verify(exactly = 1) { service.get(testUserId, 1) }
    }

    @Test
    @WithAuthUser(email = testUserName, id = testUserId)
    fun `GET_api_bookmark should return found bookmark with HTTP 200 OK`() {
        // given
        every { service.get(testUserId, 1) } returns sampleBookmark

        // when
        val resultActions: ResultActions = mockMvc.perform(MockMvcRequestBuilders.get("/bookmarks/1"))

        // then
        resultActions.andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(sampleBookmark.id))
            .andExpect(jsonPath("$.user.id").value(sampleBookmark.user.id))
            .andExpect(jsonPath("$.event.id").value(sampleBookmark.event.id))
    }

    @Test
    @WithAuthUser(email = testUserName, id = testUserId)
    fun `GET_api_bookmark should return null with HTTP 200 OK if element is not found`() {
        // given
        every { service.get(testUserId, 1) } returns null

        // when
        val resultActions: ResultActions = mockMvc.perform(MockMvcRequestBuilders.get("/bookmarks/1"))

        // then
        resultActions.andExpect(status().isOk)
            .andExpect(content().string("null"))
    }

    @Test
    @WithAuthUser(email = testUserName, id = testUserId)
    fun `DELETE_api_bookmark_{bookmarkId} should invoke service_delete`() {
        // given
        every { service.delete(testUserId, 1) } returns Unit

        // when
        mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/bookmarks/1")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        )

        // then
        verify(exactly = 1) { service.delete(testUserId, 1) }
    }

    @Test
    @WithAuthUser(email = testUserName, id = testUserId)
    fun `DELETE_api_bookmark_{bookmarkId} should return HTTP 204 No Content`() {
        // given
        every { service.delete(testUserId, 1) } returns Unit

        // when
        val resultActions: ResultActions = mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/bookmarks/1")
        )

        // then
        resultActions.andExpect(status().isNoContent)
    }
}
