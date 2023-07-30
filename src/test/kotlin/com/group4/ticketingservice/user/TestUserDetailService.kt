package com.group4.ticketingservice.user


import com.group4.ticketingservice.entity.User
import com.group4.ticketingservice.repository.UserRepository
import com.group4.ticketingservice.utils.Authority
import org.springframework.security.core.authority.SimpleGrantedAuthority

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class TestUserDetailService :
        UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return User(name = "minjun", email = "minjun3021@qwer.com", BCryptPasswordEncoder().encode("1234"),Authority.USER
        )
    }
}