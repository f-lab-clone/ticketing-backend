package com.group4.ticketingservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping("/")
    fun index(): String {
        println("Hello, World!")
        return "Hello, World!"
    }
}
