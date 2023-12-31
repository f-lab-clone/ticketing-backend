package com.group4.ticketingservice.filter

import com.group4.ticketingservice.utils.TokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

class JwtAuthorizationFilter(
    authenticationManager: AuthenticationManager?,
    jwtAuthorizationEntryPoint: JwtAuthorizationEntryPoint,
    private val tokenProvider: TokenProvider
) : BasicAuthenticationFilter(authenticationManager, jwtAuthorizationEntryPoint) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorizationHeader = request.getHeader("Authorization")
        val jwt = tokenProvider.parseBearerToken(authorizationHeader)

        lateinit var userId: String
        try {
            userId = tokenProvider.parseUserSpecification(jwt)
            val authorties = mutableListOf<GrantedAuthority>()
            authorties.add(SimpleGrantedAuthority("USER"))
            val authentication =
                UsernamePasswordAuthenticationToken.authenticated(userId.toInt(), jwt, authorties)
            SecurityContextHolder.getContext().authentication = authentication
        } catch (e: Exception) {
            request.setAttribute("exception", e)
        }

        chain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val authorization = request.getHeader("Authorization")

        return authorization == null || !authorization.startsWith("Bearer")
    }
}
