package com.group4.ticketingservice.controller


import com.group4.ticketingservice.dto.UserDto
import com.group4.ticketingservice.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun register(@RequestBody request : SignUpRequest) : ResponseEntity<UserDto>{
        try {
            val user=userService.createUser(request)
            return ResponseEntity.status(HttpStatus.CREATED).body(user)
        }catch (e : IllegalArgumentException){
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }



}

data class SignUpRequest(val email: String,
                         val name: String,
                         val password: String)