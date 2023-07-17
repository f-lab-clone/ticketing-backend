package com.group4.ticketingservice.controller


import com.group4.ticketingservice.dto.SignInRequest
import com.group4.ticketingservice.dto.SignUpRequest
import com.group4.ticketingservice.dto.UserDto
import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun register(@RequestBody request: SignUpRequest): ResponseEntity<UserDto> {
        try {
            val user = userService.createUser(request)
            return ResponseEntity.status(HttpStatus.CREATED).body(user)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }
    @PostMapping("/login")
    fun login(@RequestBody request: SignInRequest): ResponseEntity<UserDto> {
        try {
            val user = userService.login(request)
            return ResponseEntity.status(HttpStatus.OK).body(user)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @GetMapping("/test")
    fun test(@AuthenticationPrincipal user : User):ResponseEntity<Map<String,Any>>{
     return ResponseEntity.ok(mapOf("name" to user.email))
    }


}

