package com.group4.ticketingservice

import com.fasterxml.jackson.databind.ObjectMapper
import com.group4.ticketingservice.dto.SignInRequest
import com.group4.ticketingservice.utils.TokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails

import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.io.PrintWriter
import java.util.Collections


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

        val principal = authResult?.principal as UserDetails
        val jwt = tokenProvider.createToken("${principal.username}:${principal.authorities}")

        val body=JSONObject(mapOf("Authorization" to "Bearer $jwt"))
        response?.contentType="application/json"
        val writer: PrintWriter? = response?.writer
        writer?.println(body)
    }

    override fun unsuccessfulAuthentication(request: HttpServletRequest?,
                                            response: HttpServletResponse?,
                                            failed: AuthenticationException?) {
        val body=JSONObject(mapOf("message" to "Authentication failed."))
        response?.contentType="application/json"
        val writer: PrintWriter? = response?.writer
        writer?.println(body)

    }
}