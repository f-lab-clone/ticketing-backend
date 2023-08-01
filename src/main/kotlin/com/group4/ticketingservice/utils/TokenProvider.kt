package com.group4.ticketingservice.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SecurityException
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component
import java.security.Key
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
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

    fun createExpiredTokenForTest(userSpecification: String) = Jwts.builder()
        .signWith(createKey())
        .setSubject(userSpecification)
        .setIssuer(issuer)
        .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
        .setExpiration(Date.from(Instant.now().minusSeconds(100)))
        .compact()!!

    fun createWrongTokenForTest(userSpecification: String) {
        val secretBytes = Base64.getDecoder()
            .decode("bGFmZGo7ZGRkZGRkZmRhc2Rma2phcztrYWpkZjtkZmxrc2RqanNkYWZqYXNlaWZqb2FzZWppZmVqZmllamZpamVmaWplZmY=")
        // not .decode(secretKey)
        val key = SecretKeySpec(secretBytes, signatureAlgorithm.jcaName)

        Jwts.builder()
            .signWith(key)
            .setSubject(userSpecification)
            .setIssuer(issuer)
            .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
            .setExpiration(Date.from(Instant.now().plus(expirationHours, ChronoUnit.HOURS)))
            .compact()!!
    }

    fun getClaimsFromToken(token: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(createKey())
            .build()
            .parseClaimsJws(token)
            .body
    fun parseUserSpecification(token: String) =
        getClaimsFromToken(token).subject.split(":")
    fun parseBearerToken(header: String) = header.substring(7)

    fun parseTokenExpirationTime(token: String): Long {
        val expirationTime = getClaimsFromToken(token).expiration
        val localDateTime = expirationTime.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

        val expiresInMillis = ChronoUnit.MILLIS.between(LocalDateTime.now(), localDateTime)
        return expiresInMillis
    }
    fun validateToken(token: String): Boolean {
        try {
            getClaimsFromToken(token)
            return true
        } catch (e: SecurityException) {
            // "잘못된 JWT 서명입니다."
        } catch (e: MalformedJwtException) {
            // "잘못된 JWT 서명입니다."
        } catch (e: ExpiredJwtException) {
            // "만료된 JWT 토큰입니다."
        } catch (e: UnsupportedJwtException) {
            // "지원되지 않는 JWT 토큰입니다."
        } catch (e: IllegalArgumentException) {
            // "JWT 토큰이 잘못되었습니다."
        }
        return false
    }
}
