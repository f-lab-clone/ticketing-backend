package com.group4.ticketingservice.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component
import java.security.Key
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Base64
import java.util.Date
import javax.crypto.spec.SecretKeySpec

@Component
@PropertySource("classpath:application.properties")
class TokenProvider(
    @Value("\${ticketing.jwt.secret}")
    private val secretKey: String,
    @Value("\${ticketing.jwt.expiration-hours}")
    private val expirationHours: Long,
    @Value("\${ticketing.jwt.issuer}")
    private val issuer: String
) {
    private val signatureAlgorithm = SignatureAlgorithm.HS256

    fun createKey(): Key {
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

    fun getClaimsFromToken(token: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(createKey())
            .build()
            .parseClaimsJws(token)
            .body
    fun parseUserSpecification(token: String) =
        getClaimsFromToken(token).subject
    fun parseBearerToken(header: String) = header.substring(7)

    fun parseTokenExpirationTime(token: String): Long {
        val expirationTime = getClaimsFromToken(token).expiration
        val localDateTime = expirationTime.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

        val expiresInMillis = ChronoUnit.MILLIS.between(LocalDateTime.now(), localDateTime)
        return expiresInMillis
    }
}
