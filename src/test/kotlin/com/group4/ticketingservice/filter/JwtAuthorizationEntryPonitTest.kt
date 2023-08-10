package com.group4.ticketingservice.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.group4.ticketingservice.JwtAuthorizationEntryPoint
import com.group4.ticketingservice.dto.SignInRequest
import io.mockk.every
import io.mockk.mockk
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority

class JwtAuthorizationEntryPonitTest {
    private val authenticationManager: AuthenticationManager = mockk()
    private val entryPoint = JwtAuthorizationEntryPoint()

    val sampleSignInRequest = SignInRequest().apply {
        email = "minjun3021@naver.com"
        password = "123456789"
    }

    @Test
    fun `JwtAuthorizationEntryPonit_commence() should write content at response `() {
        // given
        every { authenticationManager.authenticate(any()) } returns UsernamePasswordAuthenticationToken(
            sampleSignInRequest.email,
            null,
            listOf(SimpleGrantedAuthority("USER"))
        )
        // when
        val req = MockHttpServletRequest()
        val res = MockHttpServletResponse()
        val requestJson = ObjectMapper().writeValueAsString(sampleSignInRequest)
        req.setContent(requestJson.toByteArray())

        entryPoint.commence(req, res, mockk())

        //then
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, res.status)
    }
}
