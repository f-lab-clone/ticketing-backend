package com.group4.ticketingservice.bookmark

import com.google.gson.GsonBuilder
import com.group4.ticketingservice.bookmark.BookmarkControllerTest.testFields.testUserId
import com.group4.ticketingservice.bookmark.BookmarkControllerTest.testFields.testUserName
import com.group4.ticketingservice.config.GsonConfig
import com.group4.ticketingservice.config.SecurityConfig
import com.group4.ticketingservice.controller.BookmarkController
import com.group4.ticketingservice.dto.BookmarkFromdto
import com.group4.ticketingservice.entity.Bookmark
import com.group4.ticketingservice.entity.Event
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.filter.JwtAuthorizationEntryPoint
import com.group4.ticketingservice.service.BookmarkService
import com.group4.ticketingservice.user.WithAuthUser
import com.group4.ticketingservice.utils.OffsetDateTimeAdapter
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
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Duration
import java.time.OffsetDateTime

@ExtendWith(MockKExtension::class)
@WebMvcTest(
    controllers = [BookmarkController::class],
    includeFilters = [ComponentScan.Filter(value = [(SecurityConfig::class), (JwtAuthorizationEntryPoint::class), (GsonConfig::class), (OffsetDateTimeAdapter::class), (TokenProvider::class)], type = FilterType.ASSIGNABLE_TYPE)]
)
class BookmarkControllerTest(
    @Autowired val mockMvc: MockMvc
) {
    @MockkBean
    private lateinit var service: BookmarkService
    object testFields {
        const val testUserId = 1
        const val testUserName = "james@example.com"
        const val testUserRole = "USER"
        const val password = "12345678"
    }

    private val sampleUser = User(
        name = "james",
        email = "james@example.com",
        password = "12345678",

        phone = "010-1234-5678"
    )

    private val sampleEvent: Event = Event(
        id = 1,
        name = "test title",
        startDate = OffsetDateTime.now(),
        endDate = OffsetDateTime.now(),
        reservationEndTime = OffsetDateTime.now(),
        reservationStartTime = OffsetDateTime.now(),
        maxAttendees = 10
    )

    private val sampleBookmark = Bookmark(
        id = 1,
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
                name = "정섭이의 코딩쇼",
                startDate = OffsetDateTime.now(),
                endDate = OffsetDateTime.now(),
                reservationEndTime = OffsetDateTime.now() + Duration.ofHours(2),
                reservationStartTime = OffsetDateTime.now() - Duration.ofHours(2),
                maxAttendees = 10
            )
        ),

        Bookmark(
            id = 12,
            user = sampleUser,
            event = Event(
                id = 2,
                name = "민준이의 전국군가잘함",
                startDate = OffsetDateTime.now(),
                endDate = OffsetDateTime.now(),
                reservationEndTime = OffsetDateTime.now() + Duration.ofHours(2),
                reservationStartTime = OffsetDateTime.now() - Duration.ofHours(2),
                maxAttendees = 10
            )
        ),
        Bookmark(
            id = 13,
            user = sampleUser,
            event = Event(
                id = 3,
                name = "하영이의 신작도서 팬싸인회",
                startDate = OffsetDateTime.now(),
                endDate = OffsetDateTime.now(),
                reservationEndTime = OffsetDateTime.now() + Duration.ofHours(2),
                reservationStartTime = OffsetDateTime.now() - Duration.ofHours(2),
                maxAttendees = 10
            )
        ),
        Bookmark(
            id = 14,
            user = sampleUser,
            event = Event(
                id = 4,
                name = "준하의 스파르타 코딩 동아리 설명회",
                startDate = OffsetDateTime.now(),
                endDate = OffsetDateTime.now(),
                reservationEndTime = OffsetDateTime.now() + Duration.ofHours(2),
                reservationStartTime = OffsetDateTime.now() - Duration.ofHours(2),
                maxAttendees = 10
            )
        )
    )
    val totalElements: Long = 100
    val page: Page<Bookmark> = PageImpl(content, pageable, totalElements)

    @Test
    @WithAuthUser(email = testUserName, id = testUserId)
    fun `POST_api_bookmark should invoke service_create`() {
        // given
        every { service.create(testUserId, sampleBookmarkDto) } returns sampleBookmark

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
        every { service.create(testUserId, sampleBookmarkDto) } returns sampleBookmark

        // when
        val resultActions: ResultActions = mockMvc.perform(
            post("/bookmarks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(GsonBuilder().create().toJson(sampleBookmarkDto).toString())
        )

        // then
        resultActions.andExpect(status().isCreated)
            .andExpect(jsonPath("$.data.id").value(sampleBookmark.id))
    }

    @Test
    @WithAuthUser(email = testUserName, id = testUserId)
    fun `POST_api_bookmark should return HTTP ERROR 400 for invalid parameter`() {
        // given
        every { service.create(testUserId, sampleBookmarkDto) } returns sampleBookmark

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
        every { service.getBookmarks(testUserId, pageable) } returns page

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/bookmarks"))

        // then
        verify(exactly = 1) { service.getBookmarks(any(), any()) }
    }

    @Test
    @WithAuthUser(email = testUserName, id = testUserId)
    fun `GET_api_bookmarks should return list of bookmarks with HTTP 200 OK`() {
        // given
        every { service.getBookmarks(any(), any()) } returns page

        // when
        val resultActions: ResultActions = mockMvc.perform(MockMvcRequestBuilders.get("/bookmarks"))

        // then
        resultActions.andExpect(status().isOk)
            .andExpect(jsonPath("$.data.[0].id").value(11))
    }

    @Test
    @WithAuthUser(email = testUserName, id = testUserId)
    fun `GET_api_bookmarks should return page of bookmarks with pagination`() {
        // given
        every { service.getBookmarks(any(), any()) } returns page

        // when
        val result = mockMvc.perform(
            get("/bookmarks")
                .param("page", "1")
                .param("size", "4")
        )

        // then
        result
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.totalElements").value(totalElements))
            .andExpect(jsonPath("$.data.[0].id").value(11))
            .andExpect(jsonPath("$.data.[1].id").value(12))
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
            .andExpect(jsonPath("$.data.id").value(sampleBookmark.id))
            .andExpect(jsonPath("$.data.user.id").value(sampleBookmark.user.id))
            .andExpect(jsonPath("$.data.event.id").value(sampleBookmark.event.id))
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
    fun `DELETE_api_bookmark_{bookmarkId} should return HTTP 200 OK`() {
        // given
        every { service.delete(testUserId, 1) } returns Unit

        // when
        val resultActions: ResultActions = mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/bookmarks/1")
        )

        // then
        resultActions.andExpect(status().isOk)
    }
}
