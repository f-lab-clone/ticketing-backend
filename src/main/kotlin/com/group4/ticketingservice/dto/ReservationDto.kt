package com.group4.ticketingservice.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import java.time.OffsetDateTime

data class ReservationCreateRequest(
    @field:NotNull
    @field:Positive
    val eventId: Int?,
    @field:NotNull
    val name: String?,
    @field:NotNull
    @field:Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능 합니다.")
    val phoneNumber: String?,
    @field:NotNull
    @field:Positive
    val postCode: Int?,
    @field:NotNull
    val address: String ?
)

data class ReservationUpdateRequest(
    @field:NotNull
    @field:Positive
    val eventId: Int?
)

data class ReservationDeleteRequest(
    @field:NotNull
    @field:Positive
    val id: Int?
)

data class ReservationResponse(
    val id: Int,
    val eventId: Int,
    val userId: Int,
    val createdAt: OffsetDateTime,
    val name: String,
    val phoneNumber: String,
    val postCode: Int,
    val address: String
)
