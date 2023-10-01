package com.group4.ticketingservice.utils.logging

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.util.UUID

class LogInterceptor : HandlerInterceptor {

    private val log = logger()

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        MDC.put("requestId", UUID.randomUUID().toString())
        MDC.put("userId", getUserId())
        log.info("[${request.method}] ${request.requestURI}")

        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        MDC.remove("requestId")
        MDC.remove("userId")
    }

    private fun getUserId(): String {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        return (authentication?.principal ?: "guest").toString()
    }
}
