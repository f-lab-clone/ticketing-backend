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
    val id: Int
)

class WithAuthUserSecurityContextFactory(private val tokenProvider: TokenProvider) : WithSecurityContextFactory<WithAuthUser> {
    override fun createSecurityContext(annotation: WithAuthUser): SecurityContext {
        val token = UsernamePasswordAuthenticationToken(annotation.id, tokenProvider.createToken("${annotation.email}:${annotation.id}"), listOf(SimpleGrantedAuthority("USER")))
        val context = SecurityContextHolder.getContext()
        context.authentication = token
        return context
    }
}
