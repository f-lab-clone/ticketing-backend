package com.group4.ticketingservice

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
class TestControllerTest : AbstractIntegrationTest() {
    @Autowired private lateinit var mockMvc: MockMvc

    @Test
    fun contextLoads() {
    }

    @Test
    fun helloWorldAPI() {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(status().isOk)
    }

    @Test
    fun databaseAccessAPI() {
        // init: INSERT INTO `user` (user_id, show_id) VALUES (1, 2),(1, 3),(2, 4);

        mockMvc.perform(MockMvcRequestBuilders.get("/set")) // push {show_id: 1, user_id: 1} into array[3]
            .andExpect(status().isOk)

        mockMvc.perform(MockMvcRequestBuilders.get("/list"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].user_id").value(1))
            .andExpect(jsonPath("$[0].show_id").value(2))
            .andExpect(jsonPath("$[3].user_id").value(1))
            .andExpect(jsonPath("$[3].show_id").value(1))
    }
}
