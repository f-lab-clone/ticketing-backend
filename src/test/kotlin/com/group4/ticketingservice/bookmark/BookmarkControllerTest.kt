package com.group4.ticketingservice.bookmark

import com.group4.ticketingservice.controller.BookmarkController
import com.group4.ticketingservice.dto.BookmarkFromdto
import com.group4.ticketingservice.service.BookmarkService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(BookmarkController::class)
class BookmarkControllerTest(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    private lateinit var service: BookmarkService
    private val sampleBookmark = BookmarkFromdto(
        user_id = 1,
        show_id = 1
    )

    @Test
    fun `POST_create_bookmark api should invoke service_create`() {
        every { service.create(sampleBookmark) } returns 1

        mockMvc.perform(
            post("/bookmark")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("user_id", "1")
                .param("show_id", "1")
        )
            .andExpect(status().isOk)

        verify(exactly = 1) { service.create(sampleBookmark) }
    }
}
