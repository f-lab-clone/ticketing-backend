package com.group4.ticketingservice.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

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
