package com.group4.ticketingservice.entity


import com.group4.ticketingservice.dto.SignUpRequest
import com.group4.ticketingservice.utils.Authority

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder


@Entity
@Table(name = "user")

class User(name: String, email: String, password: String,authority : Authority) : BaseTimeEntity(), UserDetails {
    companion object{
        fun from(request : SignUpRequest, encoder : PasswordEncoder)=User(
                name = request.name,
                email = request.email,
                password = encoder.encode(request.password),
                authority = Authority.USER
        )
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null

    @Column(nullable = false)
    var name: String = name
        protected set

    @Column(nullable = false)
    var email: String = email
        protected set

    @Column(nullable = false)
    var pw: String = password
        protected set

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var role : Authority =authority

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authority= mutableListOf<GrantedAuthority>()
        authority.add(SimpleGrantedAuthority(role.toString()))
        return authority
    }

    override fun getPassword(): String=pw

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean =true

    override fun isEnabled(): Boolean = true

}