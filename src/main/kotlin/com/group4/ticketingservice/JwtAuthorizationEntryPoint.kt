package com.group4.ticketingservice

import com.google.gson.GsonBuilder
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.PrintWriter

@Component
class JwtAuthorizationEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        val gson = GsonBuilder().create()
        val body = gson.toJson(mapOf("message" to "JWT Autorization failed."))
        if (response != null) {
            response.contentType = "application/json"
            val writer: PrintWriter? = response.writer
            writer?.println(body)
            response.status = HttpServletResponse.SC_UNAUTHORIZED
        }
    }
}
