package com.group4.ticketingservice.utils

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.security.Key
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
@PropertySource("classpath:application.properties")
class TokenProvider(
        @Value("\${ticketing.jwt.secret}")
        private val secretKey: String,
        @Value("\${ticketing.jwt.expiration-hours}")
        private val expirationHours: Long,
        @Value("\${ticketing.jwt.issuer}")
        private val issuer: String,
) {
    private val signatureAlgorithm = SignatureAlgorithm.HS256

    fun  createKey(): Key {
        val secretBytes = Base64.getDecoder().decode(secretKey)
        val key = SecretKeySpec(secretBytes, signatureAlgorithm.jcaName)
        return key
    }

    fun createToken(userSpecification: String) = Jwts.builder()
            .signWith(createKey())
            .setSubject(userSpecification)
            .setIssuer(issuer)
            .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
            .setExpiration(Date.from(Instant.now().plus(expirationHours, ChronoUnit.HOURS)))
            .compact()!!

    fun validateTokenAndGetSubject(token: String): String? = Jwts.parserBuilder()
            .setSigningKey(createKey())
            .build()
            .parseClaimsJws(token)
            .body
            .subject

}

