package com.group4.ticketingservice.Event

import com.group4.ticketingservice.AbstractIntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.cache.CacheManager
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@AutoConfigureMockMvc
class EventControllerTest : AbstractIntegrationTest() {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var cacheManager: CacheManager

    @Test
    fun `getEvents caches the result`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/events")
                .param("page", "0")
                .param("size", "10")

        )

        val cache = cacheManager.getCache("getEvents")
        val cachedValue = cache!!.get(0)

        assert(cachedValue != null)
    }
}
