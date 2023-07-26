package com.group4.ticketingservice.config

import com.group4.ticketingservice.JwtAuthenticationEntryPoint
import com.group4.ticketingservice.JwtAuthenticationFilter
import com.group4.ticketingservice.service.UserDetailService
import com.group4.ticketingservice.utils.TokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.CustomEditorConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component


@EnableMethodSecurity
@Configuration
class SecurityConfig(
        private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
        private val tokenProvider: TokenProvider
) {

    private val allowedUrls = arrayOf("/")

    @Bean
    fun filterChain(http: HttpSecurity): DefaultSecurityFilterChain {
        http
                .cors { it.disable() }
                .formLogin { it.disable() }
                .httpBasic { it.disable() }
                .csrf { it.disable() }
                .authorizeHttpRequests {
                    it.requestMatchers(*allowedUrls).permitAll()
                            .anyRequest().authenticated()

                }

                .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
                .exceptionHandling { it.authenticationEntryPoint(jwtAuthenticationEntryPoint) }
                .apply(CustomFilterConfigurer(tokenProvider))


        return http.build()!!
    }

    class CustomFilterConfigurer(private val tokenProvider: TokenProvider) :
            AbstractHttpConfigurer<CustomFilterConfigurer?, HttpSecurity?>() {
        override fun configure(builder: HttpSecurity?) {
            val authenticationManager = builder?.getSharedObject(AuthenticationManager::class.java)
            val jwtAuthenticationFilter = JwtAuthenticationFilter(authenticationManager, tokenProvider)
            jwtAuthenticationFilter.setFilterProcessesUrl("/users/login")
            builder?.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        }

    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}