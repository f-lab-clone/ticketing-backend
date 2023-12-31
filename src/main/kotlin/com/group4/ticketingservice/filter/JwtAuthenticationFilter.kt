package com.group4.ticketingservice.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.group4.ticketingservice.dto.SignInRequest
import com.group4.ticketingservice.dto.SuccessResponseDTO
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.utils.TokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.PrintWriter

class JwtAuthenticationFilter(
    private val authenticationManager: AuthenticationManager?,
    private val tokenProvider: TokenProvider,
    private val gson: Gson

) : UsernamePasswordAuthenticationFilter() {
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication? {
        super.setAuthenticationManager(authenticationManager)

        var signInRequest = SignInRequest()
        val om = ObjectMapper()
        try {
            signInRequest = om.readValue(request.inputStream, SignInRequest::class.java)
        } catch (e: Exception) {
            request.setAttribute("exception", e)
        }
        val authenticationToken = UsernamePasswordAuthenticationToken(signInRequest.email, signInRequest.password)
        val authentication = getAuthenticationManager().authenticate(authenticationToken)
        return authentication
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val user = authResult.principal as User
        val jwt = tokenProvider.createToken("${user.id}")
        val body = gson.toJson(
            SuccessResponseDTO(
                path = request.requestURI,
                data = mapOf("Authorization" to "Bearer $jwt")
            )
        )

        response.contentType = "application/json"
        val writer: PrintWriter = response.writer
        writer.println(body)
    }
}
