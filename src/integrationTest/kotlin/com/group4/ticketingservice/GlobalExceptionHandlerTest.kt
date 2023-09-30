package com.group4.ticketingservice

import com.group4.ticketingservice.utils.exception.ErrorCodes
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@AutoConfigureMockMvc
class GlobalExceptionHandlerTest : AbstractIntegrationTest() {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `GET_api_users_access_token_info should return HTTPStatus 403 Forbidden when jwt is expired`() {
        // expied jwt
        val jwt = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaXNzIjoia21qIiwiaWF0IjoxNjk1ODg1ODk5LCJleHAiOjE2OTU4ODU4OTl9.ihz4uE9xP_TUU_GOe2pG8JkpyVofST4qqbIILnBeA20"
        val endpoint = "/users/access_token_info"
        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.get(endpoint)
                .header("Authorization", jwt)
        )

        mvcResult.andExpect(MockMvcResultMatchers.status().isForbidden)
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(ErrorCodes.JWT_EXPIRED.errorCode))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.path").value(endpoint))
    }

    @Test
    fun `GET_api_users_access_token_info should return HTTPStatus 401 Unauthorized when jwt signature does not match`() {
        // jwt that signature does not match
        val jwt = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaXNzIjoia21qIiwiaWF0IjoxNjk1ODg1ODk5LCJleHAiOjE2OTU4ODU4OTl9.Fer9Q0h5RpY9CHuSRWhqBfjILVEFZ0w-49j5jAg46hY"
        val endpoint = "/users/access_token_info"
        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.get(endpoint)
                .header("Authorization", jwt)
        )

        mvcResult.andExpect(MockMvcResultMatchers.status().isUnauthorized)
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(ErrorCodes.JWT_AUTHENTICATION_FAILED.errorCode))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.path").value(endpoint))
    }
}
