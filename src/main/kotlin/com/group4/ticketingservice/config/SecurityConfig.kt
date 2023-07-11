package com.group4.ticketingservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class SecurityConfig {

    private val allowedUrls = arrayOf("/", "users", "users/register")

    @Bean
    fun filterChain(http: HttpSecurity) = http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(*allowedUrls).permitAll()
                        .anyRequest().authenticated()
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .build()!!

    @Bean
    fun passwordEncoder()=BCryptPasswordEncoder()
}