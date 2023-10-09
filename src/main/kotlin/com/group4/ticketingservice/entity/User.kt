package com.group4.ticketingservice.entity

import com.group4.ticketingservice.dto.UserDto
import com.group4.ticketingservice.utils.Authority
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "user")
class User(name: String, email: String, password: String, authority: Authority, id: Int? = null, phone: String) : BaseTimeEntity(), UserDetails {
    companion object {
        fun toDto(user: User) = UserDto(
            name = user.name,
            email = user.email,
            createdAt = user.createdAt
        )
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Int? = id

    @Column(nullable = false)
    @NotNull
    var name: String = name
        protected set

    @Column(nullable = false)
    @NotNull
    var email: String = email
        protected set

    @Column(nullable = false)
    @NotNull
    var pw: String = password
        protected set

    @Column(nullable = false)
    @NotNull
    var phoneNumber: String = phone
        protected set

    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    var role: Authority = authority

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authority = mutableListOf<GrantedAuthority>()
        authority.add(SimpleGrantedAuthority(role.toString()))
        return authority
    }

    override fun getPassword(): String = pw

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
