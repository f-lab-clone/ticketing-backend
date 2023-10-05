package com.group4.ticketingservice.filter

import com.group4.ticketingservice.utils.logging.logger
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.MDC
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.util.ContentCachingRequestWrapper
import java.net.URLEncoder
import java.util.UUID

class LogFilter : Filter {

    private val log = logger()

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpServletRequest = ContentCachingRequestWrapper(request as HttpServletRequest)

        MDC.put("requestId", UUID.randomUUID().toString())

        val queryParams = getQueryParams(request)
        val requestInfo = "[${request.method}] ${request.requestURI}$queryParams"

        log.info("{}  userId : {}", requestInfo, getUserId())

        chain.doFilter(httpServletRequest, response)

        var reqContent = String(httpServletRequest.contentAsByteArray)
        reqContent = reqContent.replace("\n", "").replace("\r", "")

        if (reqContent != "") {
            reqContent = "RequestBody : $reqContent"
            log.info("{}", reqContent)
        }
        MDC.remove("requestId")
    }

    private fun getUserId() = SecurityContextHolder.getContext().authentication.principal.toString()

    private fun getQueryParams(request: HttpServletRequest): String {
        val paramMap = request.parameterMap
        val params = StringBuilder()
        if (paramMap.isNotEmpty()) params.append("?")
        for ((key, value1) in paramMap) {
            if (params.isNotEmpty()) params.append("&")
            for (value in value1) {
                params.append(URLEncoder.encode(key, "UTF-8"))
                params.append("=")
                params.append(URLEncoder.encode(value, "UTF-8"))
            }
        }

        return params.toString()
    }
}
