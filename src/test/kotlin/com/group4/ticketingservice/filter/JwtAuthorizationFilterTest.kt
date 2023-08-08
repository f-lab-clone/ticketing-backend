package com.group4.ticketingservice.filter

import com.group4.ticketingservice.JwtAuthorizationEntryPoint
import com.group4.ticketingservice.utils.TokenProvider
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.spy
import org.springframework.mock.web.MockFilterChain
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContextHolderStrategy

class JwtAuthorizationFilterTest {
    private val tokenProvider: TokenProvider = mockk()
    private val authenticationManager: AuthenticationManager = mockk()
    private val entryPoint: JwtAuthorizationEntryPoint = mockk()
    val jwtAuthorizationFilter = JwtAuthorizationFilter(authenticationManager, entryPoint, tokenProvider)

    val testUserName = "minjun3021@qwer.com"
    val testUserRole = "USER"

    @Test
    fun `JwtAuthorizationFilterTest_doFilterInternal() should set Authentication when jwt is valid `() {
        // given
        every { tokenProvider.parseBearerToken(any()) } returns ""
        every { tokenProvider.validateToken(any()) } returns true
        every { tokenProvider.parseUserSpecification(any()) } returns listOf(testUserName, testUserRole)

        // when
        val req = MockHttpServletRequest()
        val res = MockHttpServletResponse()
        val chain = MockFilterChain()
        req.addHeader("Authorization", "Bearer ~~~~")
        val strategy: SecurityContextHolderStrategy = spy(SecurityContextHolder.getContextHolderStrategy())
        jwtAuthorizationFilter.setSecurityContextHolderStrategy(strategy)
        jwtAuthorizationFilter.doFilter(req, res, chain)

        val authenticationPrincipal = strategy.context.authentication.principal
        // then
        Assertions.assertThat(authenticationPrincipal == testUserName).isTrue()
    }

    @Test
    fun `JwtAuthorizationFilterTest_doFilterInternal() should not set Authentication when jwt is invalid `() {
        // given
        every { tokenProvider.parseBearerToken(any()) } returns ""
        every { tokenProvider.validateToken(any()) } returns false
        every { tokenProvider.parseUserSpecification(any()) } returns listOf(testUserName, testUserRole)

        // when
        val req = MockHttpServletRequest()
        val res = MockHttpServletResponse()
        val chain = MockFilterChain()
        req.addHeader("Authorization", "Bearer ~~~~")
        val strategy: SecurityContextHolderStrategy = spy(SecurityContextHolder.getContextHolderStrategy())
        strategy.context.authentication = null // 다른 테스트에 영향을 받아서 추가
        jwtAuthorizationFilter.setSecurityContextHolderStrategy(strategy)
        jwtAuthorizationFilter.doFilter(req, res, chain)

        // then
        Assertions.assertThat(strategy.context.authentication == null).isTrue()
    }
}
