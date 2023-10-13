package com.group4.ticketingservice.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping()
class HealthController {

    @GetMapping()
    fun healthCheck1(): ResponseEntity<Any> {
        val headers = HttpHeaders()
        headers.set("Content-Location", "/health")

        return ResponseEntity("OK", headers, HttpStatus.OK)
    }

    @GetMapping("/health")
    fun healthCheck2(): ResponseEntity<Any> {
        val headers = HttpHeaders()
        headers.set("Content-Location", "/health")

        return ResponseEntity("OK", headers, HttpStatus.OK)
    }
}
