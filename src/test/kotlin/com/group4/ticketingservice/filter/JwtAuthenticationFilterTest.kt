package com.group4.ticketingservice.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.group4.ticketingservice.dto.SignInRequest
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.utils.Authority
import com.group4.ticketingservice.utils.OffsetDateTimeAdapter
import com.group4.ticketingservice.utils.TokenProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockFilterChain
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.time.OffsetDateTime

class JwtAuthenticationFilterTest {
    private val tokenProvider: TokenProvider = mockk()
    private val authenticationManager: AuthenticationManager = mockk()
    private val gson: Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapter(OffsetDateTime::class.java, OffsetDateTimeAdapter())
        .create()
    private val filter: JwtAuthenticationFilter = JwtAuthenticationFilter(authenticationManager, tokenProvider, gson)

    val sampleSignInRequest = SignInRequest().apply {
        email = "minjun3021@naver.com"
        password = "123456789"
    }
    val sampleUser = User(
        name = "minjun3021@qwer.com",
        email = "minjun",
        password = "1234",
        authority = Authority.USER,
        phone = "010-1234-5678"
    )
    val sample = "{}"

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
        assertThat(result).isNotNull()
        assertEquals(sampleSignInRequest.email, result?.principal)
    }

    @Test
    fun `JwtAuthenticationFilter_dofilter() should call tokenProvider_createToken() at JwtAuthenticationFilter_successfulAuthentication when credential is good `() {
        // given
        every { tokenProvider.createToken(any()) } returns "Bearer ~~~"
        every { authenticationManager.authenticate(any()) } returns UsernamePasswordAuthenticationToken(
            sampleUser,
            null,
            listOf(SimpleGrantedAuthority("USER"))
        )
        // when
        val req = MockHttpServletRequest("POST", "/login")
        req.servletPath = "/login"
        val res = MockHttpServletResponse()
        val chain = MockFilterChain()

        val requestJson = ObjectMapper().writeValueAsString(sampleSignInRequest)
        req.setContent(requestJson.toByteArray())

        filter.doFilter(req, res, chain)

        // then
        verify(exactly = 1) { tokenProvider.createToken(any()) }
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

        // then
        assertThrows(BadCredentialsException::class.java) {
            filter.attemptAuthentication(req, res)
        }
    }
}
