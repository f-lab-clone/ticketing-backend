package com.group4.ticketingservice.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.GsonBuilder
import com.group4.ticketingservice.dto.SignInRequest
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.utils.TokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.io.PrintWriter


class JwtAuthenticationFilter(
        authenticationManager: AuthenticationManager?,
        tokenProvider: TokenProvider

) : UsernamePasswordAuthenticationFilter() {

    private var authenticationManager: AuthenticationManager? = authenticationManager
    private var tokenProvider: TokenProvider = tokenProvider


    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication? {
        super.setAuthenticationManager(authenticationManager)
        val om = ObjectMapper()
        try {
            val signInRequest = om.readValue(request?.inputStream, SignInRequest::class.java)
            val authenticationToken = UsernamePasswordAuthenticationToken(signInRequest.email, signInRequest.password)
            val authentication = getAuthenticationManager().authenticate(authenticationToken)
            return authentication
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null;
    }


    override fun successfulAuthentication(request: HttpServletRequest?,
                                          response: HttpServletResponse?,
                                          chain: FilterChain?,
                                          authResult: Authentication?) {

        val user = authResult?.principal as User
        val jwt = tokenProvider.createToken("${user.username}:${user.role}")
        val gson=GsonBuilder().create()
        val body=gson.toJson(mapOf("Authorization" to "Bearer $jwt"))
        response?.contentType="application/json"
        val writer: PrintWriter? = response?.writer
        writer?.println(body)
    }

    override fun unsuccessfulAuthentication(request: HttpServletRequest?,
                                            response: HttpServletResponse?,
                                            failed: AuthenticationException?) {
        val gson= GsonBuilder().create()
        val body=gson.toJson(mapOf("message" to "Authentication failed."))
        response?.contentType="application/json"
        response?.status=HttpServletResponse.SC_BAD_REQUEST
        val writer: PrintWriter? = response?.writer
        writer?.println(body)

    }
}