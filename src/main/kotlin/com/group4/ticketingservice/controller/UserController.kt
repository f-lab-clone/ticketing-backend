package com.group4.ticketingservice.controller

import com.group4.ticketingservice.dto.UserCreateRequest
import com.group4.ticketingservice.dto.UserResponse
import com.group4.ticketingservice.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {

    @PostMapping
    fun createUser(@RequestBody request: UserCreateRequest): ResponseEntity<UserResponse> {
        val user = userService.createUser(request.name, request.email)
        val response = UserResponse(
            id = user.id!!,
            name = user.name,
            email = user.email
        )
        return ResponseEntity.ok(response)
    }
}
