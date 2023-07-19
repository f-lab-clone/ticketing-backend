package com.group4.ticketingservice.controller

import com.group4.ticketingservice.dto.UserFromdto
import com.group4.ticketingservice.service.TestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController @Autowired constructor(val testService: TestService) {
    @GetMapping("/")
    fun index(): String {
        println("Hello, World!")
        return "Hello, World!"
    }

    @GetMapping("/set")
    fun add(): ResponseEntity<Any> {
        val userFormDto = UserFromdto(1, 1)
        val created = testService.create(userFormDto)
        return ResponseEntity.ok().body(created)
    }

    @GetMapping("/list")
    fun get(): ResponseEntity<Any> {
        return ResponseEntity.ok().body(testService.getList())
    }
}
