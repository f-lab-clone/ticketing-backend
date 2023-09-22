package com.group4.ticketingservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping("/test500")
    fun throwError(): String {
        throw RuntimeException("This is a test error.")
    }
}
