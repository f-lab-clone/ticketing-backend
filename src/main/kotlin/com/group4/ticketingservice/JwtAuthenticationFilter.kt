package com.group4.ticketingservice

import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.service.UserDetailService
import com.group4.ticketingservice.utils.Authority
import com.group4.ticketingservice.utils.TokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Order(0)
@Component
class JwtAuthenticationFilter(
        private val tokenProvider: TokenProvider,
        private val userDetailService: UserDetailService
) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse,
                                  filterChain: FilterChain) {

        val token = parseBearerToken(request)

        if (token == null) {
            SecurityContextHolder.getContext().authentication = null;
            filterChain.doFilter(request, response);
            return;
        }

        val user = parseUserSpecification(token)
        UsernamePasswordAuthenticationToken.authenticated(user, token, user.authorities)
                .apply { details = WebAuthenticationDetails(request) }
                .also { SecurityContextHolder.getContext().authentication = it }

        filterChain.doFilter(request, response)

    }


    private fun parseBearerToken(request: HttpServletRequest) = request.getHeader(HttpHeaders.AUTHORIZATION)
            ?.takeIf { it?.startsWith("Bearer", true) ?: false }?.substring(7)

    private fun parseUserSpecification(token: String?) = (
            token?.takeIf { it.length >= 10 }
                    ?.let { tokenProvider.validateTokenAndGetSubject(it) }
                    ?: let { "anonymous:anonymous" }
            ).split(":")
            .let { userDetailService.loadUserByUsername(it[0]) }


}