package com.group4.ticketingservice

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.json.JSONObject
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.PrintWriter




@Component
class JwtAuthorizationEntryPoint : AuthenticationEntryPoint {

    override fun commence(request: HttpServletRequest?,
                          response: HttpServletResponse?,
                          authException: AuthenticationException?) {

        val body= JSONObject(mapOf("message" to "JWT Autorization failed."))
        response?.contentType="application/json"
        val writer: PrintWriter? = response?.writer
        writer?.println(body)
        response?.status=HttpServletResponse.SC_UNAUTHORIZED




    }
}