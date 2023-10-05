package com.group4.ticketingservice.controller

import com.group4.ticketingservice.utils.exception.CustomException
import com.group4.ticketingservice.utils.exception.ErrorCodes
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Hidden
@Profile("test")
@RestController
class TestController {
    @GetMapping("/test500")
    fun throwError(): String {
        throw RuntimeException("This is a test error.")
    }

    @PostMapping("/test")
    fun test(@RequestBody request: SampleDTO) {
        throw CustomException(ErrorCodes.TEST_ERROR)
    }
}

data class SampleDTO(
    val text: String
)
