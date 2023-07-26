package com.group4.ticketingservice

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.PrintWriter




@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(request: HttpServletRequest?,
                          response: HttpServletResponse?,
                          authException: AuthenticationException?) {
        response?.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UnAuthorized");
        val writer: PrintWriter? = response?.writer
        writer?.println("entrypoint")

    }
}