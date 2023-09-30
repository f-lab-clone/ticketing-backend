package com.group4.ticketingservice.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.group4.ticketingservice.dto.SignInRequest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView

class JwtAuthorizationEntryPonitTest {

    private val resolver: HandlerExceptionResolver = mockk()
    private val authenticationManager: AuthenticationManager = mockk()
    private val entryPoint = JwtAuthorizationEntryPoint(resolver)
    private val modelAndView: ModelAndView = mockk()

    val sampleSignInRequest = SignInRequest().apply {
        email = "minjun3021@naver.com"
        password = "123456789"
    }

    @Test
    fun `JwtAuthorizationEntryPoint_commence() should invoke resolver_resolveException`() {
        // given
        every { resolver.resolveException(any(), any(), any(), any()) } returns modelAndView

        // when
        val req = MockHttpServletRequest()
        val res = MockHttpServletResponse()
        val requestJson = ObjectMapper().writeValueAsString(sampleSignInRequest)
        req.setContent(requestJson.toByteArray())

        entryPoint.commence(req, res, mockk())

        // then
        verify(exactly = 1) { resolver.resolveException(any(), any(), any(), any()) }
    }
}
