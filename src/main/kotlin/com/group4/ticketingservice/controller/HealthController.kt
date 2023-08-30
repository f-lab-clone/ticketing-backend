package com.group4.ticketingservice.controller

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
        return ResponseEntity.status(HttpStatus.OK).body("OK")
    }

    @GetMapping("/health")
    fun healthCheck2(): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.OK).body("OK")
    }
}
