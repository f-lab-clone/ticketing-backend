package com.group4.ticketingservice.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.group4.ticketingservice.JwtAuthorizationEntryPoint
import com.group4.ticketingservice.dto.SignInRequest
import com.group4.ticketingservice.utils.TokenProvider
import io.mockk.every
import io.mockk.mockk
import jakarta.servlet.http.HttpServletResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority

class FilterTest {
    private val tokenProvider: TokenProvider = mockk()
    private val authenticationManager: AuthenticationManager = mockk()
    private val filter: JwtAuthenticationFilter = JwtAuthenticationFilter(authenticationManager, tokenProvider)
    private val entryPoint = JwtAuthorizationEntryPoint()

    val sampleSignInRequest = SignInRequest().apply {
        email = "minjun3021@naver.com"
        password = "123456789"
    }

    @Test
    fun `JwtAuthenticationFilter_attemptAuthentication() should return Authentication when credential is good `() {
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

        val result: Authentication? = filter.attemptAuthentication(req, res)

        // then
        assertThat(result != null).isTrue()
        Assertions.assertEquals(sampleSignInRequest.email, result?.principal)
    }

    @Test
    fun `JwtAuthenticationFilter_attemptAuthentication() should throw exception  when credential is bad `() {
        // given
        every { authenticationManager.authenticate(any()) } throws BadCredentialsException("")
        // when
        val req = MockHttpServletRequest()
        val res = MockHttpServletResponse()
        val requestJson = ObjectMapper().writeValueAsString(sampleSignInRequest)
        req.setContent(requestJson.toByteArray())

        assertThrows(BadCredentialsException::class.java) {
            filter.attemptAuthentication(req, res)
        }
    }

    @Test
    fun `JwtAuthenticationFilter_attemptAuthentication() should return null when request parameter is urlencoded `() {
        // given
        every { authenticationManager.authenticate(any()) } throws BadCredentialsException("")
        // when
        val req = MockHttpServletRequest()
        val res = MockHttpServletResponse()
        req.contentType = "application/x-www-form-urlencoded"
        req.addParameter("email", sampleSignInRequest.email)
        req.addParameter("password", sampleSignInRequest.password)

        val result = filter.attemptAuthentication(req, res)

        assertThat(result == null).isTrue()
    }

    @Test
    fun `JwtAuthenticationFilter_successfulAuthentication() should write content at response `() {
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
        assertThat(res.status == HttpServletResponse.SC_UNAUTHORIZED).isTrue()
    }
}
