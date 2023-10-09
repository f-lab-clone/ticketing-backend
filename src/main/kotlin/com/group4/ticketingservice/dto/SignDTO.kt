package com.group4.ticketingservice.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

/**
 * 클래스 생성자에서 매개변수들을 받아 필드에 포함시키는 경우에는
 * Validation관련 어노테이션들이 필드에 붙는것이아닌
 * 생성자에 붙으므로 필드에 어노테이션을 적용해주겠다는 @field를 붙혀야함
 * ex) @NotNull -> @field:NotNull
 */

data class SignUpRequest(
    @field:NotNull
    @field:Email
    val email: String?,
    @field:NotNull
    @field:Size(min = 2, max = 30)
    val name: String?,
    @field:NotNull
    @field:Size(min = 9, max = 128)
    val password: String?,
    @field:NotNull
    @field:Pattern(regexp = "^01(?:0|1|[6-9])[.-](\\d{3}|\\d{4})[.-](\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능 합니다.")
    val phoneNumber: String?

)

class SignInRequest {
    var email: String = ""
    var password: String = ""
}
