package com.group4.ticketingservice.user

import com.group4.ticketingservice.utils.TokenProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContext
import org.springframework.security.test.context.support.WithSecurityContextFactory

@Retention(AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = WithAuthUserSecurityContextFactory::class)
annotation class WithAuthUser(
    val email: String,
    val role: String
)

class WithAuthUserSecurityContextFactory(private val tokenProvider: TokenProvider) : WithSecurityContextFactory<WithAuthUser> {
    override fun createSecurityContext(annotation: WithAuthUser): SecurityContext {
        val token = UsernamePasswordAuthenticationToken(annotation.email, tokenProvider.createToken("${annotation.email}:${annotation.role}"), listOf(SimpleGrantedAuthority(annotation.role)))
        val context = SecurityContextHolder.getContext()
        context.authentication = token
        return context
    }
}
